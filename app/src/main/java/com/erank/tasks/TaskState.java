package com.erank.tasks;

public enum TaskState {
    PROCESSING, READY, DONE;

    public String capitalizedName() {
        String name = name();
        name = name.substring(0, 1) + name.substring(1).toLowerCase();
        return name;
    }
}
