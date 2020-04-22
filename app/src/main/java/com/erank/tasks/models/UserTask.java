package com.erank.tasks.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

import static com.erank.tasks.utils.room.Converters.dateToTimestamp;
import static com.erank.tasks.utils.room.Converters.stateToOrdinal;
import static com.erank.tasks.utils.room.Converters.toDate;
import static com.erank.tasks.utils.room.Converters.toState;

@Entity(tableName = "tasks")
public class UserTask implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    public static final Parcelable.Creator<UserTask> CREATOR = new Parcelable.Creator<UserTask>() {
        @Override
        public UserTask createFromParcel(Parcel source) {
            return new UserTask(source);
        }

        @Override
        public UserTask[] newArray(int size) {
            return new UserTask[size];
        }
    };
    private TaskState state;
    private String details;

    public UserTask() {
    }

    private Date timestamp;

    @Ignore
    public UserTask(String details, TaskState state) {
        timestamp = new Date();
        this.details = details;
        this.state = state;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public UserTask(String details) {
        this(details, TaskState.TO_DO);
    }

    protected UserTask(Parcel in) {
        this.id = in.readLong();
        this.details = in.readString();
        this.state = toState(in.readInt());
        this.timestamp = toDate(in.readLong());
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTask userTask = (UserTask) o;
        return id == userTask.id &&
                Objects.equals(details, userTask.details) &&
                state == userTask.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, state);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.details);
        dest.writeInt(stateToOrdinal(state));
        dest.writeLong(dateToTimestamp(timestamp));
    }
}
