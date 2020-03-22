package com.erank.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance(UserTask task) {

        Bundle args = new Bundle();
        args.putParcelable("task", task);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView descTv = view.findViewById(R.id.desc_tv);
        TextView stateTv = view.findViewById(R.id.state_tv);

        Bundle args = getArguments();
        if (args == null) return view;

        UserTask task = args.getParcelable("task");
        descTv.setText(task.getDescription());
        stateTv.setText(task.getState().capitalizedName());

        return view;
    }
}
