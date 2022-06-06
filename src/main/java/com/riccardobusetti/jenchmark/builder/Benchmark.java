package com.riccardobusetti.jenchmark.builder;

import com.riccardobusetti.jenchmark.engine.execution.Step;

import java.util.List;

public class Benchmark<T> {

    private final Class<T> clazz;
    private final String methodName;
    private final Class<?>[] paramsTypes;
    private final Object[] params;
    private final boolean withGenerator;
    private final List<Step<Integer>> steps;

    public Benchmark(Class<T> clazz, String methodName, Class<?>[] paramsTypes, Object[] params, boolean withGenerator, List<Step<Integer>> steps) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.paramsTypes = paramsTypes;
        this.params = withGenerator ? null : params;
        this.withGenerator = withGenerator;
        this.steps = steps;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParamsTypes() {
        return paramsTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public boolean withGenerator() {
        return withGenerator;
    }

    public List<Step<Integer>> getSteps() {
        return steps;
    }
}
