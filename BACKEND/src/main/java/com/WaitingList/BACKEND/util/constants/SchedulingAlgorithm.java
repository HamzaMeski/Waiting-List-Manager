package com.WaitingList.BACKEND.util.constants;

public enum SchedulingAlgorithm {
    FIFO("First In First Out"),
    PRIORITY("High Priority First"),
    SJF("Shortest Job First");

    private final String description;

    SchedulingAlgorithm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 