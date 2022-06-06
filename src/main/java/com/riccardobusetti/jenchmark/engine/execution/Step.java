package com.riccardobusetti.jenchmark.engine.execution;

public interface Step<T> {

    StepType type();

    StepValue<T> value();

    enum StepType {
        REPEAT,
        PAUSE
    }

    class StepValue<T> {
        private final T value;

        public StepValue(T value) {
            this.value = value;
        }

        public T get() {
            if (value != null) {
                return value;
            }

            throw new RuntimeException("This step, doesn't have any value");
        }
    }
}
