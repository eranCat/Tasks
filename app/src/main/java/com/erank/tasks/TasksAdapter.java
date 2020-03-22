package com.erank.tasks;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskVH> {

    private List<UserTask> tasks;
    private TaskClickCallback callback;

    public TasksAdapter() {
        tasks = new ArrayList<>();
    }

    public void setCallback(TaskClickCallback callback) {
        this.callback = callback;
    }

    public void setTasks(List<UserTask> tasks) {
        this.tasks = tasks;
    }

    public void add(UserTask task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public void remove(UserTask task) {
        int i = tasks.indexOf(task);
        this.removeTask(i);
    }

    private void removeTask(int i) {
        tasks.remove(i);
        notifyItemRemoved(i);
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
        holder.itemView.setOnClickListener(v -> callback.onTaskTapped(task, pos));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void sort() {
        Collections.sort(tasks, (task, t1) -> task.getState().compareTo(t1.getState()));
        notifyDataSetChanged();
    }

    static class TaskVH extends RecyclerView.ViewHolder {

        private final TextView descET;

        TaskVH(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task, parent, false));

            descET = itemView.findViewById(R.id.desc_tv);
        }

        void fill(UserTask task) {
            descET.setText(task.getDescription());

            switch (task.getState()) {
                case PROCESSING:
                    itemView.setBackgroundColor(Color.GREEN);
                    break;

                case READY:
                    itemView.setBackgroundColor(Color.WHITE);
                    break;

                case DONE:
                    itemView.setBackgroundColor(0xFFF57C00);
                    break;
            }

        }
    }
}
