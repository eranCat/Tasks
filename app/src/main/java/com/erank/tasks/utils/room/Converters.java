package com.erank.tasks.utils.room;

import androidx.room.TypeConverter;

import com.erank.tasks.models.TaskState;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public static TaskState toState(Integer ordinal) {
        if (ordinal == null) return null;

        TaskState[] values = TaskState.values();
        if (ordinal < 0 || ordinal >= values.length)
            return null;

        return values[ordinal];
    }

    @TypeConverter
    public static Integer stateToOrdinal(TaskState state) {
        return state.ordinal();
    }
}