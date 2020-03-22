package com.erank.tasks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskClickCallback {

    private final int RC_ADD = 123;//check
    private TasksAdapter tasksAdapter;
    private View container;
    private DataManger dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = DataManger.getInstance();

        findViewById(R.id.add)
                .setOnClickListener(v -> openAddActivity());

        tasksAdapter = new TasksAdapter();
        tasksAdapter.setCallback(this);

        List<UserTask> tasks = dm.getTasks();
        tasksAdapter.setTasks(tasks);

        RecyclerView tasksRV = findViewById(R.id.recyclerView);
        tasksRV.setAdapter(tasksAdapter);

        container = findViewById(R.id.inner_container);
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
                tasksAdapter.notifyItemInserted(tasks.size() - 1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTaskTapped(UserTask task, int pos) {
        switch (task.getState()) {

            case READY:
                task.setState(TaskState.PROCESSING);
                break;
            case PROCESSING:
                task.setState(TaskState.DONE);
                break;
            case DONE:
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                break;
        }
        tasksAdapter.notifyItemChanged(pos);

        container.setVisibility(View.VISIBLE);
        updateFragment(task);
    }

    private void updateFragment(UserTask task) {
        InfoFragment fragment = InfoFragment.newInstance(task);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.inner_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MenuItem item = menu.findItem(R.id.sort);

            ColorStateList colors = new ColorStateList(
                    new int[][]{{0}},
                    new int[]{Color.WHITE});
            item.setIconTintList(colors);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            tasksAdapter.sort();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
