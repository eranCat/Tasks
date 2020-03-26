package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.erank.tasks.R;
import com.erank.tasks.models.UserTask;

public class TaskCell extends LinearLayout {
    private TextView descET;

    public TaskCell(Context context) {
        super(context);
        init();
    }

    public TaskCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TaskCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TaskCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.task, this, true);

        descET = findViewById(R.id.desc_tv);
    }

    public void setTask(UserTask task) {
        descET.setText(task.getDescription());

        switch (task.getState()) {
            case PROCESSING:
                setBackgroundColor(Color.YELLOW);
                break;

            case TO_DO:
                setBackgroundColor(Color.WHITE);
                break;

            case DONE:
                setBackgroundColor(Color.GREEN);
                break;
        }

    }
}
