package com.erank.tasks.utils.customViews;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.OnStateSelectedCallback;
import com.erank.tasks.models.TaskState;

public class TaskStatesSpinner extends AppCompatSpinner implements AdapterView.OnItemSelectedListener {
    private OnStateSelectedCallback callback;
    private TaskState selectedState;

    public TaskStatesSpinner(Context context) {
        super(context);
        init(context);
    }


    public TaskStatesSpinner(Context context, int mode) {
        super(context, mode);
        init(context);
    }

    public TaskStatesSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TaskStatesSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TaskStatesSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init(context);
    }

    public TaskStatesSpinner(Context context, AttributeSet attrs, int defStyleAttr,
                             int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
        init(context);
    }

    private void init(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, R.layout.spinner_item, R.id.option_tv);

        for (TaskState state : TaskState.values()) {
            arrayAdapter.add(state.capitalizedName());
        }

        setAdapter(arrayAdapter);

        setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TaskState state = TaskState.values()[i];
        selectedState = state;
        if (callback != null) {
            callback.onSelected(state);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setCallback(OnStateSelectedCallback callback) {
        this.callback = callback;
    }

    public TaskState getSelectedState() {
        return selectedState;
    }
}
