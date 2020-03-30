package com.erank.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.room.Repo;

import static android.R.layout.simple_spinner_dropdown_item;

public class InfoFragment extends Fragment
        implements InfoUpdatable, OnItemSelectedListener {

    private TextView descTv;
    private TextView stateTv;
    private Spinner stateSpinner;
    private UserTask task;
    private Repo repo;

    public static InfoFragment newInstance(long taskId) {

        Bundle args = new Bundle();
        args.putLong("id", taskId);
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


        descTv = view.findViewById(R.id.desc_tv);
        stateTv = view.findViewById(R.id.state_tv);
        stateSpinner = view.findViewById(R.id.state_spinner);

        Context context = getContext();

        Bundle args = getArguments();
        final long taskID = args.getLong("id", -1);
        repo = Repo.getInstance();
        task = repo.getTask(taskID);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, simple_spinner_dropdown_item);

        for (TaskState state : TaskState.values()) {
            arrayAdapter.add(state.capitalizedName());
        }

        stateSpinner.setAdapter(arrayAdapter);
        stateSpinner.setOnItemSelectedListener(this);

        updateInfo(task);
    }

    @Override
    public void updateInfo(UserTask task) {
        this.task = task;
        if (getView() == null) return;

        descTv.setText(task.getDescription());
        TaskState state = task.getState();
        stateTv.setText(state.capitalizedName());
        stateSpinner.setSelection(state.ordinal());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = getContext();
        if (context instanceof ItemUpdatable) {
            TaskState state = TaskState.values()[i];
            task.setState(state);
            repo.updateTask(task);
            ((ItemUpdatable) context).UpdateItem(task.getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
