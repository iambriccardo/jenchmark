package com.riccardobusetti.jenchmark.engine;

import com.riccardobusetti.jenchmark.builder.Benchmark;
import com.riccardobusetti.jenchmark.engine.measurement.Measurement;

import java.util.List;

public class BenchmarkResult<T> {

    private final Benchmark<T> benchmark;
    private final List<Measurement> measurements;

    public BenchmarkResult(Benchmark<T> benchmark, List<Measurement> measurements) {
        this.benchmark = benchmark;
        this.measurements = measurements;
    }

    public Benchmark<T> getBenchmark() {
        return benchmark;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }
}
