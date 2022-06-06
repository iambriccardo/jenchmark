package com.riccardobusetti.jenchmark.annotations;

import com.riccardobusetti.jenchmark.generation.BenchmarkGenerator;
import com.riccardobusetti.jenchmark.generation.FallbackGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Benchmark {

    String name();

    Class<? extends BenchmarkGenerator> fallbackGenerator() default FallbackGenerator.class;
}