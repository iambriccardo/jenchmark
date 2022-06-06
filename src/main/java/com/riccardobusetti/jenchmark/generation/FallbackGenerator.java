package com.riccardobusetti.jenchmark.generation;

public class FallbackGenerator implements BenchmarkGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public Object generateValueFor(Class<?> clazz) {
        throw new RuntimeException("Couldn't find a generator for Class<" + clazz + ">");
    }
}
