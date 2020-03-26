package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.erank.tasks.models.TaskState;

public class TodoFilterButton extends FilterButton {

    private static final TaskState STATE = TaskState.TO_DO;
    private static final int BG_COLOR = android.R.color.holo_red_light;

    public TodoFilterButton(Context context) {
        super(context);
    }

    public TodoFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoFilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TaskState getState() {
        return STATE;
    }

    @Override
    protected int getColor() {
        return getResources().getColor(BG_COLOR);
    }
}
