package com.riccardobusetti.jenchmark.builder;

import com.riccardobusetti.jenchmark.engine.execution.Step;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkBuilder<T> {

    public BenchmarkBuilderStep1 of(Class<T> clazz) {
        return new BenchmarkBuilderStep1(clazz);
    }

    public final class BenchmarkBuilderStep1 {

        private final Class<T> clazz;

        public BenchmarkBuilderStep1(Class<T> clazz) {
            this.clazz = clazz;
        }

        public BenchmarkBuilderStep2 method(String methodName, Class<?>... paramsTypes) {
            return new BenchmarkBuilderStep2(clazz, methodName, paramsTypes);
        }
    }

    public final class BenchmarkBuilderStep2 {

        private final Class<T> clazz;
        private final String methodName;
        private final Class<?>[] paramsTypes;

        public BenchmarkBuilderStep2(Class<T> clazz, String methodName, Class<?>[] paramsTypes) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.paramsTypes = paramsTypes;
        }

        public BenchmarkBuilderStep3 withParams(Object... params) {
            return new BenchmarkBuilderStep3(clazz, methodName, paramsTypes, params);
        }

        public BenchmarkBuilderStep3 withGenerator() {
            return new BenchmarkBuilderStep3(clazz, methodName, paramsTypes);
        }
    }

    public final class BenchmarkBuilderStep3 {
        private final Class<T> clazz;
        private final String methodName;
        private final Class<?>[] paramsTypes;
        private final Object[] params;
        private final boolean withGenerator;
        private final List<Step<Integer>> steps;

        public BenchmarkBuilderStep3(Class<T> clazz, String methodName, Class<?>[] paramsTypes, Object[] params) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.paramsTypes = paramsTypes;
            this.params = params;
            this.withGenerator = false;
            this.steps = new ArrayList<>();
        }

        public BenchmarkBuilderStep3(Class<T> clazz, String methodName, Class<?>[] paramsTypes) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.paramsTypes = paramsTypes;
            this.params = null;
            this.withGenerator = true;
            this.steps = new ArrayList<>();
        }

        public BenchmarkBuilderStep3 repeat(int times) {
            steps.add(new Step<>() {
                @Override
                public StepType type() {
                    return StepType.REPEAT;
                }

                @Override
                public StepValue<Integer> value() {
                    return new StepValue<>(times);
                }
            });

            return this;
        }

        public BenchmarkBuilderStep3 pause(int time) {
            steps.add(new Step<>() {
                @Override
                public StepType type() {
                    return StepType.PAUSE;
                }

                @Override
                public StepValue<Integer> value() {
                    return new StepValue<>(time);
                }
            });

            return this;
        }

        public Benchmark<T> build() {
            return new Benchmark<>(
                    clazz,
                    methodName,
                    paramsTypes,
                    params,
                    withGenerator,
                    steps
            );
        }
    }
}
