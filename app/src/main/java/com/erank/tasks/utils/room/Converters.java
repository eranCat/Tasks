package com.erank.tasks.utils.room;

import androidx.room.TypeConverter;

import com.erank.tasks.models.TaskState;

import java.util.Date;

public class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public TaskState fromState(Integer ordinal) {
        return ordinal == null ? null : TaskState.values()[ordinal];
    }

    @TypeConverter
    public Integer stateToOrdinal(TaskState state) {
        return state.ordinal();
    }
}