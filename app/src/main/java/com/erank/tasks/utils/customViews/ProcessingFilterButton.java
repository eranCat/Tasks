package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.util.AttributeSet;

import com.erank.tasks.models.TaskState;

public class ProcessingFilterButton extends FilterButton {
    private static final int BG_COLOR = 0xFFF9A825;
    private static final TaskState STATE = TaskState.PROCESSING;

    public ProcessingFilterButton(Context context) {
        super(context);
    }

    public ProcessingFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProcessingFilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TaskState getState() {
        return STATE;
    }

    @Override
    protected int getColor() {
        return BG_COLOR;
    }
}
