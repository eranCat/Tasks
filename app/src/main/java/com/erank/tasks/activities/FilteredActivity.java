package com.erank.tasks.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.TasksAdapter;
import com.erank.tasks.utils.room.Repo;

public class FilteredActivity extends AppCompatActivity implements TaskClickCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);

        TaskState state = (TaskState) getIntent().getSerializableExtra("state");

        TasksAdapter tasksAdapter = new TasksAdapter(this);
        tasksAdapter.setTasks(Repo.getInstance().getFilteredTasks(state));

        RecyclerView tasksRv = findViewById(R.id.tasks_rv);
        tasksRv.setAdapter(tasksAdapter);
    }

    @Override
    public void onTaskTapped(UserTask task) {
        Toast.makeText(this, task.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
