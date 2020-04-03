package com.erank.tasks.utils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.UserTask;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends ListAdapter<UserTask, TaskVH> {

    private TaskClickCallback callback;

    public TasksAdapter(List<UserTask> tasks, TaskClickCallback callback) {
        super(DiffCallback.create());
        this.callback = callback;
        submitList(tasks);
    }

    public TasksAdapter(TaskClickCallback callback) {
        this(new ArrayList<>(), callback);
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int pos) {
        holder.fill(getItem(pos), callback);
    }

}
