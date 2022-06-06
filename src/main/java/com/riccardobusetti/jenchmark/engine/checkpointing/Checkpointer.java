package com.riccardobusetti.jenchmark.engine.checkpointing;

import com.riccardobusetti.jenchmark.engine.BenchmarkEngineState;

public interface Checkpointer {

    BenchmarkEngineState engineState();

    void onCheckpoint(String checkpointName, Object data);

    default void onCheckpoint(String checkpointName) {
        onCheckpoint(checkpointName, null);
    }
}
