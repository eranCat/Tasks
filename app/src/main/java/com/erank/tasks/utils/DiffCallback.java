package com.erank.tasks.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.erank.tasks.models.UserTask;

public class DiffCallback extends DiffUtil.ItemCallback<UserTask> {

    public static AsyncDifferConfig<UserTask> create() {
        return new AsyncDifferConfig.Builder<>(new DiffCallback()).build();
    }

    @Override
    public boolean areItemsTheSame(@NonNull UserTask oldItem, @NonNull UserTask newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull UserTask oldItem, @NonNull UserTask newItem) {
        return oldItem.equals(newItem);
    }
}
