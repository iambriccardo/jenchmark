import com.riccardobusetti.jenchmark.builder.Benchmark;
import com.riccardobusetti.jenchmark.builder.BenchmarkBuilder;
import com.riccardobusetti.jenchmark.engine.BenchmarkEngine;
import com.riccardobusetti.jenchmark.engine.BenchmarkResult;
import com.riccardobusetti.jenchmark.engine.measurement.Measurement;
import com.riccardobusetti.jenchmark.sample.TestBenchmark;

import java.lang.reflect.InvocationTargetException;

class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        BenchmarkEngine engine = new BenchmarkEngine();
        Benchmark<TestBenchmark> benchmark = new BenchmarkBuilder<TestBenchmark>()
                .of(TestBenchmark.class)
                .method("webPlusDb")
                .withParams()
                .repeat(1)
                .pause(1000)
                .repeat(1)
                .build();

        BenchmarkResult<TestBenchmark> result = engine.benchmark(benchmark);
        for (Measurement measurement : result.getMeasurements()) {
            System.out.println(measurement);
        }
    }
}