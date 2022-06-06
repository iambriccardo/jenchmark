package com.riccardobusetti.jenchmark.sample;


import com.riccardobusetti.jenchmark.annotations.Benchmark;
import com.riccardobusetti.jenchmark.annotations.BenchmarkParam;
import com.riccardobusetti.jenchmark.annotations.InjectCheckpointer;
import com.riccardobusetti.jenchmark.engine.BenchmarkEngine;
import com.riccardobusetti.jenchmark.engine.checkpointing.Checkpointer;
import com.riccardobusetti.jenchmark.generation.BaseGenerator;

@Benchmark(name = "TestBenchmark", fallbackGenerator = BaseGenerator.class)
@InjectCheckpointer(checkpointer = BenchmarkEngine.EngineCheckpointer.class)
public class TestBenchmark {

    private final Checkpointer checkpointer;

    public TestBenchmark(Checkpointer checkpointer) {
        this.checkpointer = checkpointer;
    }

    @BenchmarkParam(type = Long.class)
    @BenchmarkParam(type = Long.class)
    public void webPlusDbParams(long webDelay, long dbDelay) throws InterruptedException {
        Thread.sleep(webDelay);
        checkpointer.onCheckpoint("Web request");

        Thread.sleep(dbDelay);
        checkpointer.onCheckpoint("Database query");
    }

    public void webPlusDb() throws InterruptedException {
        Thread.sleep(1000);
        checkpointer.onCheckpoint("Web request");

        Thread.sleep(500);
        checkpointer.onCheckpoint("Database query");
    }
}