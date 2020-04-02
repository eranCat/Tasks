package com.erank.tasks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.fragments.InfoFragment;
import com.erank.tasks.interfaces.DeleteCallback;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.TasksAdapter;
import com.erank.tasks.utils.room.Repo;

public class MainActivity extends AppCompatActivity
        implements TaskClickCallback, ItemUpdatable, DeleteCallback,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {

    private final int RC_ADD = 123;//check
    private TasksAdapter tasksAdapter;

    private boolean isSorting;

    private void openAddActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivityForResult(intent, RC_ADD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add).setOnClickListener(v -> openAddActivity());

        RecyclerView tasksRV = findViewById(R.id.tasks_rv);

        Repo.getTasks(tasks -> {
            tasksAdapter = new TasksAdapter(tasks, this);
            tasksRV.setAdapter(tasksAdapter);
        });

        findViewById(R.id.todo_btn)
                .setOnClickListener(v -> filterTasks(TaskState.TO_DO));

        findViewById(R.id.process_btn)
                .setOnClickListener(v -> filterTasks(TaskState.PROCESSING));

        findViewById(R.id.done_btn)
                .setOnClickListener(v -> filterTasks(TaskState.DONE));

        findViewById(R.id.reset_btn)
                .setOnClickListener(v -> resetData());
    }

    private void filterTasks(TaskState state) {
        Repo.getFilteredTasks(state, tasks -> {
            tasksAdapter.submitList(tasks);
            tasksAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_ADD && resultCode == RESULT_OK) {
            Repo.getTasks(tasks -> {
                tasksAdapter.submitList(tasks);
                tasksAdapter.notifyItemInserted(tasks.size() - 1);

                new AlertDialog.Builder(this)
                        .setTitle("Good news")
                        .setMessage("Added successfully!")
                        .setPositiveButton("OK", null)
                        .show();
            });
        }
    }

    @Override
    public void onTaskTapped(UserTask task, int pos) {
        FragmentManager manager = getSupportFragmentManager();

        final String tag = "infoFragment";

        Fragment infoFragment = manager.findFragmentByTag(tag);

        if (infoFragment != null) {
            ((InfoUpdatable) infoFragment).updateInfo(task, pos);
        } else {
            InfoFragment fragment = InfoFragment.newInstance(task.getId(), pos);
            manager.beginTransaction()
                    .replace(R.id.inner_container, fragment, tag)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchItem.setOnActionExpandListener(this);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            isSorting = !isSorting;
            if (!isSorting) {
                resetData();
            } else {
                Repo.getTasksOrderedByState(tasks -> {
                    tasksAdapter.submitList(tasks);
                    tasksAdapter.notifyDataSetChanged();
                });
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateItem(UserTask task, int taskPos) {
        Repo.getTasks(tasks -> {
            tasksAdapter.submitList(tasks);
            tasksAdapter.notifyItemChanged(taskPos);
        });
    }

    @Override
    public void onDelete(int pos, UserTask task) {
        Repo.getTasks(tasks -> {
            tasksAdapter.submitList(tasks);
            tasksAdapter.notifyItemRemoved(pos);
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Repo.getFilteredTasks(newText, tasks -> {
            tasksAdapter.submitList(tasks);
            tasksAdapter.notifyDataSetChanged();
        });
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        resetData();
        return true;
    }

    private void resetData() {
        Repo.getTasks(tasks -> {
            tasksAdapter.submitList(tasks);
            tasksAdapter.notifyDataSetChanged();
        });
    }
}
