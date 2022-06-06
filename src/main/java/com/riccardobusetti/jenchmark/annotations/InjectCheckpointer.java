package com.riccardobusetti.jenchmark.annotations;

import com.riccardobusetti.jenchmark.engine.checkpointing.Checkpointer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectCheckpointer {

    Class<? extends Checkpointer> checkpointer();
}
