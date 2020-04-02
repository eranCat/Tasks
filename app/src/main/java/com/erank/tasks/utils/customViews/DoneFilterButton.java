package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.erank.tasks.models.TaskState;

public class DoneFilterButton extends FilterButton {
    private static final TaskState STATE = TaskState.DONE;
    private static final int BG_COLOR = android.R.color.holo_green_dark;

    public DoneFilterButton(Context context) {
        super(context);
    }

    public DoneFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoneFilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TaskState getState() {
        return STATE;
    }

    @Override
    protected int getColor() {
        return ContextCompat.getColor(getContext(), BG_COLOR);
    }
}
