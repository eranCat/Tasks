package com.erank.tasks.models;

public enum TaskState {
    TO_DO, PROCESSING, DONE;

    public String capitalizedName() {
        String name = name().replace("_", " ");
        name = name.substring(0, 1) + name.substring(1).toLowerCase();
        return name;
    }
}
