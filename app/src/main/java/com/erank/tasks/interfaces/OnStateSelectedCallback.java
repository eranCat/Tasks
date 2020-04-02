package com.erank.tasks.interfaces;

import com.erank.tasks.models.TaskState;

public interface OnStateSelectedCallback {
    void onSelected(TaskState state);
}
