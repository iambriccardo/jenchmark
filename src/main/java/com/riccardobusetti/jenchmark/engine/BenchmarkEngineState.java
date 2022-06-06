package com.riccardobusetti.jenchmark.engine;

import com.riccardobusetti.jenchmark.engine.measurement.Measurement;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkEngineState {

    private final List<Measurement> measurements;
    private long currentStartTime;
    private long lastCheckpointTime;

    public BenchmarkEngineState() {
        this.measurements = new ArrayList<>();
    }

    public BenchmarkEngineState(long currentStartTime) {
        this();
        this.currentStartTime = currentStartTime;
    }

    public static BenchmarkEngineState empty() {
        return new BenchmarkEngineState();
    }

    public long getCurrentStartTime() {
        return currentStartTime;
    }

    public void setCurrentStartTime(long currentStartTime) {
        this.currentStartTime = currentStartTime;
    }

    public long getLastCheckpointTime() {
        return lastCheckpointTime;
    }

    public void setLastCheckpointTime(long lastCheckpointTime) {
        this.lastCheckpointTime = lastCheckpointTime;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
}
