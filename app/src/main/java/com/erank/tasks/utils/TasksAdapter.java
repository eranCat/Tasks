package com.erank.tasks.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskCell;

import java.util.List;

public class TasksAdapter extends ListAdapter<UserTask, TasksAdapter.TaskVH> {

    private TaskClickCallback callback;

    public TasksAdapter(List<UserTask> tasks, TaskClickCallback callback) {
        super(getDiffCallback());
        this.callback = callback;
        submitList(tasks);
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(parent);
    }

    private static AsyncDifferConfig<UserTask> getDiffCallback() {
        return new AsyncDifferConfig.Builder<>(new DiffCallback()).build();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int pos) {
        UserTask task = getItem(pos);
        holder.fill(task);
        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onTaskTapped(task, pos);
            }
        });
    }

    static class TaskVH extends RecyclerView.ViewHolder {

        private TaskCell cell;

        TaskVH(@NonNull ViewGroup parent) {
            super(inflate(parent));
            cell = itemView.findViewById(R.id.cell);
        }

        private static View inflate(@NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return inflater.inflate(R.layout.task_cell_custom, parent, false);
        }

        void fill(UserTask task) {
            cell.setTask(task);
        }
    }

    private static class DiffCallback extends DiffUtil.ItemCallback<UserTask> {

        @Override
        public boolean areItemsTheSame(@NonNull UserTask oldItem, @NonNull UserTask newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserTask oldItem, @NonNull UserTask newItem) {
            return oldItem.equals(newItem);
        }
    }
}
