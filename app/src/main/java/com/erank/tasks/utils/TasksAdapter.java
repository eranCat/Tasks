package com.erank.tasks.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskCell;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskVH> {

    private List<UserTask> tasks;
    private TaskClickCallback callback;

    public TasksAdapter(TaskClickCallback callback) {
        this.callback = callback;
        tasks = new ArrayList<>();
    }

    public void setTasks(List<UserTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int pos) {
        UserTask task = tasks.get(pos);
        holder.fill(task);
        holder.itemView.setOnClickListener(v -> callback.onTaskTapped(task));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskVH extends RecyclerView.ViewHolder {

        private TaskCell cell;

        TaskVH(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task_cell, parent, false));

            cell = itemView.findViewById(R.id.cell);
        }


        void fill(UserTask task) {
            cell.setTask(task);
        }
    }
}
