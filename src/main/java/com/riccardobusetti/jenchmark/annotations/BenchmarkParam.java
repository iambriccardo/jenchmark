package com.riccardobusetti.jenchmark.annotations;

import com.riccardobusetti.jenchmark.generation.BenchmarkGenerator;
import com.riccardobusetti.jenchmark.generation.VoidGenerator;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(BenchmarkParams.class)
public @interface BenchmarkParam {

    Class<?> type();

    Class<? extends BenchmarkGenerator> generator() default VoidGenerator.class;
}