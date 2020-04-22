package com.erank.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.DeleteCallback;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.interfaces.OnStateSelectedCallback;
import com.erank.tasks.interfaces.TextWatcherAdapter;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskStatesSpinner;
import com.erank.tasks.utils.room.Repo;
import com.erank.tasks.utils.room.callbacks.FetchTaskCallback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoFragment extends BottomSheetDialogFragment
        implements OnStateSelectedCallback,
        TextWatcherAdapter,
        FetchTaskCallback,
        InfoUpdatable {

    private EditText descEt;
    private TextView stateTv;
    private TextView dateTv;
    private TaskStatesSpinner stateSpinner;
    private UserTask task;
    private int taskPos;

    public static InfoFragment newInstance(long taskId, int pos) {

        Bundle args = new Bundle();
        args.putLong("id", taskId);
        args.putInt("pos", pos);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descEt = view.findViewById(R.id.desc_tv);
        stateTv = view.findViewById(R.id.state_tv);
        stateSpinner = view.findViewById(R.id.state_spinner);
        dateTv = view.findViewById(R.id.date_tv);
        Button deleteBtn = view.findViewById(R.id.delete_btn);

        Bundle args = getArguments();
        final long taskID = args.getLong("id", -1);
        taskPos = args.getInt("pos");

        deleteBtn.setOnClickListener(v -> delete());

        Repo.getTask(taskID, this);
    }


    @Override
    public void afterTextChanged(Editable editable) {
        String s = editable.toString();
        if (s.isEmpty()) {
            descEt.setError("Add some details");
            return;
        }

        task.setDetails(s);
        Repo.updateTask(() -> {
            Context context = getContext();
            if (context instanceof ItemUpdatable) {
                ((ItemUpdatable) context).updateItem(task, taskPos);
            }
        }, task);
    }

    private void delete() {
        if (task == null) return;

        Repo.deleteTask(task, () -> {

            dismiss();

            Context context = getContext();
            if (context instanceof DeleteCallback) {
                ((DeleteCallback) context).onDelete(task, taskPos);
            }
        });
    }

    @Override
    public void updateInfo(UserTask task, int taskPos) {
        this.task = task;
        this.taskPos = taskPos;
        if (getView() == null || task == null) return;

        descEt.setText(task.getDetails());
        TaskState state = task.getState();
        stateTv.setText(state.capitalizedName());

        stateSpinner.setSelection(state.ordinal());

        Date timestamp = task.getTimestamp();
        if (timestamp == null) return;

        DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
        String formatted = formatter.format(timestamp);
        dateTv.setText(formatted);
    }

    @Override
    public void onTaskFetched(UserTask task) {
        updateInfo(task, taskPos);
        stateSpinner.setCallback(this);
        descEt.addTextChangedListener(this);
    }

    @Override
    public void onSelected(TaskState state) {
        task.setState(state);
        Repo.updateTask(() -> {
            Context context = getContext();
            if (context instanceof ItemUpdatable) {
                ((ItemUpdatable) context).updateItem(task, taskPos);
            }
        }, task);
    }
}
