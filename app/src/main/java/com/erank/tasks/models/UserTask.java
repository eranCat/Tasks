package com.erank.tasks.models;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tasks")
public class UserTask {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String description;
    private TaskState state;

    public UserTask() {
    }

    @Ignore
    public UserTask(String description, TaskState state) {
        this.description = description;
        this.state = state;
    }

    @Ignore
    public UserTask(String description) {
        this(description, TaskState.TO_DO);
    }


    protected UserTask(Parcel in) {
        this.description = in.readString();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : TaskState.values()[tmpState];
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTask userTask = (UserTask) o;
        return id == userTask.id &&
                Objects.equals(description, userTask.description) &&
                state == userTask.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, state);
    }
}
