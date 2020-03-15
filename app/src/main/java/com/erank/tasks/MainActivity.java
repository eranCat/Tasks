package com.erank.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int RC_ADD = 123;//check
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add).setOnClickListener(view -> openAddActivity());

        tasksAdapter = new TasksAdapter();
        RecyclerView tasksRV = findViewById(R.id.recyclerView);
        tasksRV.setAdapter(tasksAdapter);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, CreateEditActivity.class);
        startActivityForResult(intent, RC_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_ADD) {
            if (resultCode == RESULT_OK) {
                List<UserTask> tasks = DataManger.getInstance().getTasks();
                tasksAdapter.setTasks(tasks);
                tasksAdapter.notifyItemInserted(tasks.size()-1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
