package com.riccardobusetti.jenchmark.engine;

import com.riccardobusetti.jenchmark.annotations.Benchmark;
import com.riccardobusetti.jenchmark.annotations.BenchmarkParam;
import com.riccardobusetti.jenchmark.annotations.InjectCheckpointer;
import com.riccardobusetti.jenchmark.engine.checkpointing.Checkpointer;
import com.riccardobusetti.jenchmark.engine.execution.Step;
import com.riccardobusetti.jenchmark.engine.measurement.Measurement;
import com.riccardobusetti.jenchmark.generation.BenchmarkGenerator;
import com.riccardobusetti.jenchmark.generation.FallbackGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BenchmarkEngine {

    private final BenchmarkEngineState benchmarkEngineState;

    public BenchmarkEngine() {
        this.benchmarkEngineState = BenchmarkEngineState.empty();
    }

    public <T> BenchmarkResult<T> benchmark(com.riccardobusetti.jenchmark.builder.Benchmark<T> benchmark) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
        Class<T> clazz = benchmark.getClazz();
        String methodName = benchmark.getMethodName();
        Class<?>[] paramsTypes = benchmark.getParamsTypes();

        if (clazz.getAnnotation(Benchmark.class) == null) {
            throw new RuntimeException("The class " + clazz + " does not use the @Benchmarkable annotation");
        }

        Constructor<T> constructor = clazz.getAnnotation(InjectCheckpointer.class) == null ?
                clazz.getConstructor() : clazz.getConstructor(Checkpointer.class);
        Method method = clazz.getMethod(methodName, paramsTypes);
        T instance = clazz.getAnnotation(InjectCheckpointer.class) == null ?
                constructor.newInstance() : constructor.newInstance(
                clazz.getAnnotation(InjectCheckpointer.class)
                        .checkpointer()
                        .getConstructor(BenchmarkEngineState.class)
                        .newInstance(benchmarkEngineState)
        );

        executeSteps(
                benchmark,
                () -> method.invoke(instance, loadBenchmarkParams(benchmark))
        );

        return new BenchmarkResult<>(benchmark, benchmarkEngineState.getMeasurements());
    }

    private <T> void executeSteps(com.riccardobusetti.jenchmark.builder.Benchmark<T> benchmark, Callback callback) throws InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Step<Integer> step : benchmark.getSteps()) {
            switch (step.type()) {
                case REPEAT:
                    int times = step.value().get();
                    for (int i = 0; i < times; i++) {
                        System.out.println("Executing " + benchmark.getMethodName());

                        long start = System.currentTimeMillis();
                        benchmarkEngineState.setCurrentStartTime(start);
                        benchmarkEngineState.setLastCheckpointTime(start);

                        callback.call();

                        long end = System.currentTimeMillis();

                        benchmarkEngineState.getMeasurements().add(new Measurement(
                                Measurement.MeasurementType.TIME,
                                benchmark.getMethodName(),
                                end - benchmarkEngineState.getCurrentStartTime()
                        ));

                        System.out.println(benchmark.getMethodName() + " executed");
                    }
                    break;
                case PAUSE:
                    System.out.println("Pausing for " + step.value().get());
                    Thread.sleep(step.value().get());
                    break;
            }
        }
    }

    private <T> Object[] loadBenchmarkParams(com.riccardobusetti.jenchmark.builder.Benchmark<T> benchmark) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<T> clazz = benchmark.getClazz();
        String methodName = benchmark.getMethodName();
        Class<?>[] paramsTypes = benchmark.getParamsTypes();

        Object[] valuedParams = new Object[paramsTypes.length];

        if (benchmark.withGenerator()) {
            Method method = clazz.getMethod(methodName, paramsTypes);
            BenchmarkParam[] benchmarkParams = method.getAnnotationsByType(BenchmarkParam.class);

            int i = 0;
            for (BenchmarkParam param : benchmarkParams) {
                BenchmarkGenerator defaultGenerator;
                BenchmarkGenerator primaryGenerator = param
                        .generator()
                        .getConstructor()
                        .newInstance();
                BenchmarkGenerator classGenerator = clazz
                        .getAnnotation(Benchmark.class)
                        .fallbackGenerator()
                        .getConstructor()
                        .newInstance();

                if (primaryGenerator.supports(param.type())) {
                    defaultGenerator = primaryGenerator;
                } else if (classGenerator.supports(param.type())) {
                    defaultGenerator = classGenerator;
                } else {
                    defaultGenerator = new FallbackGenerator();
                }

                valuedParams[i] = defaultGenerator.generateValueFor(param.type());
                i++;
            }
        } else {
            valuedParams = benchmark.getParams();
        }

        return valuedParams;
    }

    @FunctionalInterface
    public interface Callback {

        void call() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    }

    public static class EngineCheckpointer implements Checkpointer {

        private final BenchmarkEngineState benchmarkEngineState;

        public EngineCheckpointer(BenchmarkEngineState benchmarkEngineState) {
            this.benchmarkEngineState = benchmarkEngineState;
        }

        @Override
        public BenchmarkEngineState engineState() {
            return benchmarkEngineState;
        }

        @Override
        public void onCheckpoint(String checkpointName, Object data) {
            System.out.println("Checkpointing " + checkpointName);

            long checkpointTime = System.currentTimeMillis();
            long executionTime = checkpointTime - engineState().getLastCheckpointTime();

            benchmarkEngineState.addMeasurement(new Measurement(
                    Measurement.MeasurementType.TIME,
                    checkpointName,
                    executionTime
            ));

            benchmarkEngineState.setLastCheckpointTime(checkpointTime);
        }
    }
}
