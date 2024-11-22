package com.WaitingList.BACKEND.util.constants;

public enum ServiceTime {
    CONTINUOUS("En continu"),
    MORNING("Matin"),
    AFTERNOON("Après-midi");

    private final String description;

    ServiceTime(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 