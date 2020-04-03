package com.erank.tasks.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskCell;

class TaskVH extends RecyclerView.ViewHolder {

    private TaskCell cell;

    TaskVH(@NonNull ViewGroup parent) {
        super(inflate(parent));
        cell = itemView.findViewById(R.id.cell);
    }

    private static View inflate(@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.task_cell_custom, parent, false);
    }

    void fill(UserTask task, TaskClickCallback callback) {
        cell.setTask(task);
        itemView.setOnClickListener(v -> {
            if (callback != null)
                callback.onTaskTapped(task, getAdapterPosition());
        });
    }
}
