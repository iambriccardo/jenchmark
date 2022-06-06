package com.riccardobusetti.jenchmark.generation;

public class VoidGenerator implements BenchmarkGenerator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public Object generateValueFor(Class<?> clazz) {
        return null;
    }
}
