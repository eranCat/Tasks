package com.erank.tasks.utils.customViews;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatButton;

import com.erank.tasks.activities.FilteredActivity;
import com.erank.tasks.models.TaskState;

public abstract class FilterButton extends AppCompatButton {
    public FilterButton(Context context) {
        super(context);
        init();
    }

    public FilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setOnClickListener(v -> {
            Context context = getContext();
            Intent intent = new Intent(context, FilteredActivity.class);
            intent.putExtra("state", getState());
            context.startActivity(intent);
        });

        setBackgroundColor(getColor());
        setTextColor(Color.WHITE);
        setAllCaps(false);
        setText(getState().capitalizedName());
    }

    protected abstract TaskState getState();

    protected abstract @ColorInt
    int getColor();
}
