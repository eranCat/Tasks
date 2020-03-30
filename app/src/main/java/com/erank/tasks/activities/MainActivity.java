package com.erank.tasks.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.fragments.InfoFragment;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.TasksAdapter;
import com.erank.tasks.utils.room.Repo;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TaskClickCallback, ItemUpdatable {

    private final int RC_ADD = 123;//check
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add).setOnClickListener(v -> openAddActivity());

        RecyclerView tasksRV = findViewById(R.id.tasks_rv);
        tasksAdapter = new TasksAdapter(this);
        tasksRV.setAdapter(tasksAdapter);

        refreshTask();
    }

    private void refreshTask() {
        List<UserTask> tasks = Repo.getInstance().getTasks();
        tasksAdapter.setTasks(tasks);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivityForResult(intent, RC_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_ADD && resultCode == RESULT_OK) {
            List<UserTask> tasks = Repo.getInstance().getTasks();
            tasksAdapter.setTasks(tasks);
            tasksAdapter.notifyItemInserted(tasks.size() - 1);
        }
    }

    @Override
    public void onTaskTapped(UserTask task) {
        FragmentManager manager = getSupportFragmentManager();

        final String tag = "infoFragment";

        Fragment infoFragment = manager.findFragmentByTag(tag);

        if (infoFragment != null) {
            ((InfoUpdatable) infoFragment).updateInfo(task);
        } else {
            InfoFragment fragment = InfoFragment.newInstance(task.getId());
            manager.beginTransaction()
                    .replace(R.id.inner_container, fragment, tag)
                    .commit();
        }

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
            List<UserTask> tasks = Repo.getInstance().getTasksOrderedByState();
            tasksAdapter.setTasks(tasks);
            tasksAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void UpdateItem(long id) {
        refreshTask();
        tasksAdapter.notifyDataSetChanged();
    }
}
