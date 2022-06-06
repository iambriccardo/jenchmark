package com.riccardobusetti.jenchmark.generation;

public interface BenchmarkGenerator {

    boolean supports(Class<?> clazz);

    Object generateValueFor(Class<?> clazz);
}
