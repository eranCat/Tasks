package com.erank.tasks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import static android.R.layout.simple_spinner_dropdown_item;

public class InfoFragment extends Fragment implements InfoUpdatable {

    private TextView descTv;
    private TextView stateTv;
    private Spinner stateSpinner;

    public static InfoFragment newInstance(UserTask task, int pos) {

        Bundle args = new Bundle();
        args.putParcelable("task", task);
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

        Bundle args = getArguments();
        UserTask task = args.getParcelable("task");
        final int taskPos = args.getInt("pos", -1);

        descTv = view.findViewById(R.id.desc_tv);
        stateTv = view.findViewById(R.id.state_tv);
        stateSpinner = view.findViewById(R.id.state_spinner);

        Context context = getContext();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, simple_spinner_dropdown_item);

        for (TaskState state : TaskState.values()) {
            arrayAdapter.add(state.capitalizedName());
        }

        stateSpinner.setAdapter(arrayAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (context instanceof ItemUpdatable) {
                    TaskState state = TaskState.values()[i];
                    task.setState(state);
                    ((ItemUpdatable) context).UpdateItem(taskPos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateInfo(task);
    }

    @Override
    public void updateInfo(UserTask task) {
        if (getView() == null) return;

        descTv.setText(task.getDescription());
        TaskState state = task.getState();
        stateTv.setText(state.capitalizedName());
        stateSpinner.setSelection(state.ordinal());
    }
}
