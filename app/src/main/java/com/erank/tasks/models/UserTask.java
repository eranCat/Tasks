package com.erank.tasks.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserTask implements Parcelable {
    public static final Parcelable.Creator<UserTask> CREATOR =
            new Parcelable.Creator<UserTask>() {
        @Override
        public UserTask createFromParcel(Parcel source) {
            return new UserTask(source);
        }

        @Override
        public UserTask[] newArray(int size) {
            return new UserTask[size];
        }
    };

    private String description;
    private TaskState state;


    public UserTask(String description, TaskState state) {
        this.description = description;
        this.state = state;
    }

    public UserTask(String description) {
        this(description, TaskState.TO_DO);
    }

    protected UserTask(Parcel in) {
        this.description = in.readString();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : TaskState.values()[tmpState];
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
    }
}
