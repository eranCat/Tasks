package com.erank.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.DeleteCallback;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.interfaces.OnStateSelectedCallback;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskStatesSpinner;
import com.erank.tasks.utils.room.Repo;

public class InfoFragment extends Fragment
        implements InfoUpdatable, TextWatcher, OnStateSelectedCallback {

    private EditText descEt;
    private TextView stateTv;
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
        Button deleteBtn = view.findViewById(R.id.delete_btn);

        Bundle args = getArguments();
        final long taskID = args.getLong("id", -1);

        deleteBtn.setOnClickListener(v -> delete());

        Repo.getTask(taskID, task -> {
            updateInfo(task, args.getInt("pos"));
            stateSpinner.setCallback(this);
            descEt.addTextChangedListener(this);
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String s = editable.toString();
        if (s.isEmpty()) {
            descEt.setError("Give a description");
            return;
        }

        task.setDescription(s);
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

            getFragmentManager().beginTransaction().remove(this).commit();

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

        descEt.setText(task.getDescription());
        TaskState state = task.getState();
        stateTv.setText(state.capitalizedName());

        stateSpinner.setSelection(state.ordinal());
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
