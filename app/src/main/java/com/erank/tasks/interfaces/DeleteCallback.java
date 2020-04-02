package com.erank.tasks.interfaces;

import com.erank.tasks.models.UserTask;

public interface DeleteCallback {
    void onDelete(int pos, UserTask task);
}
