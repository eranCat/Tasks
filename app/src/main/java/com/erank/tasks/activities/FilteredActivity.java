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

        RecyclerView tasksRv = findViewById(R.id.tasks_rv);

        Repo.getFilteredTasks(state, tasks ->
                tasksRv.setAdapter(new TasksAdapter(tasks, this)));
    }

    @Override
    public void onTaskTapped(UserTask task, int pos) {
        Toast.makeText(this, task.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
