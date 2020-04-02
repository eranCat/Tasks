package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
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
        inflate(getContext(), R.layout.task_item, this);

        descET = findViewById(R.id.desc_tv);
    }

    public void setTask(UserTask task) {
        descET.setText(task.getDescription());

        switch (task.getState()) {
            case TO_DO:
                setBgColor(R.color.colorTodo);
                break;

            case PROCESSING:
                setBgColor(R.color.colorProcessing);
                break;

            case DONE:
                setBgColor(R.color.colorDone);
                break;
        }
    }

    private void setBgColor(@ColorRes int colorRes) {
        Resources rss = getResources();
        int color = rss.getColor(colorRes);
        Drawable round = rss.getDrawable(R.drawable.round);
        round.setTint(color);
        setBackground(round);
    }
}
