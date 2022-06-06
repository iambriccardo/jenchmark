package com.riccardobusetti.jenchmark.engine.measurement;

public class Measurement {

    private final MeasurementType type;
    private final String name;
    private final Object value;

    public Measurement(MeasurementType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public MeasurementType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public enum MeasurementType {
        TIME
    }
}
