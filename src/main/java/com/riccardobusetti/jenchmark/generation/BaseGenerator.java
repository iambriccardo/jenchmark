package com.riccardobusetti.jenchmark.generation;

import java.util.HashMap;

public class BaseGenerator implements BenchmarkGenerator {

    private final HashMap<Class<?>, Object> supportedTypes = new HashMap<>();

    public BaseGenerator() {
        initSupportedTypes();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return supportedTypes.get(clazz) != null;
    }

    @Override
    public Object generateValueFor(Class<?> clazz) {
        return supportedTypes.get(clazz);
    }

    private void initSupportedTypes() {
        supportedTypes.put(Integer.class, 100);
        supportedTypes.put(String.class, "Hello World");
    }
}
