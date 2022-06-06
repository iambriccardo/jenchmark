package com.riccardobusetti.jenchmark.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BenchmarkParams {

    BenchmarkParam[] value();
}
