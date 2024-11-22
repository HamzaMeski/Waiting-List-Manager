package com.WaitingList.BACKEND.util.constants;

public enum VisitorStatus {
    WAITING("In waiting room"),
    IN_PROGRESS("Currently being served"),
    FINISHED("Service completed"),
    CANCELLED("Service cancelled");

    private final String description;

    VisitorStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 