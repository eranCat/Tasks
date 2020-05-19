package com.erank.tasks.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.erank.tasks.R;
import com.erank.tasks.fragments.InfoFragment;
import com.erank.tasks.interfaces.DeleteCallback;
import com.erank.tasks.interfaces.InfoUpdatable;
import com.erank.tasks.interfaces.ItemUpdatable;
import com.erank.tasks.interfaces.TaskClickCallback;
import com.erank.tasks.interfaces.onSwipeCallback;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.SwipeToDeleteCallback;
import com.erank.tasks.utils.TasksAdapter;
import com.erank.tasks.utils.room.Repo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.erank.tasks.models.TaskState.DONE;
import static com.erank.tasks.models.TaskState.PROCESSING;
import static com.erank.tasks.models.TaskState.TO_DO;

public class MainActivity extends AppCompatActivity
        implements TaskClickCallback, ItemUpdatable, DeleteCallback,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener, onSwipeCallback {

    @Inject
    MainViewModel viewModel;

    final String INFO_FRAGMENT_TAG = "infoFragment";
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
        tasksAdapter = new TasksAdapter(this);
        tasksRV.setAdapter(tasksAdapter);

        SwipeToDeleteCallback callback = new SwipeToDeleteCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(tasksRV);

        resetData();

        findViewById(R.id.todo_btn)
                .setOnClickListener(v -> filterTasks(TO_DO));

        findViewById(R.id.process_btn)
                .setOnClickListener(v -> filterTasks(PROCESSING));

        findViewById(R.id.done_btn)
                .setOnClickListener(v -> filterTasks(DONE));

        findViewById(R.id.reset_btn)
                .setOnClickListener(v -> resetData());
    }

    private void filterTasks(TaskState state) {
        Repo.getFilteredTasks(state, tasksAdapter::submitList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_ADD && resultCode == RESULT_OK) {
            Repo.getTasks(tasks -> {
                tasksAdapter.submitList(tasks);
                toast("Added successfully!");
            });
        }
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskTapped(UserTask task, int pos) {
        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = manager.findFragmentByTag(INFO_FRAGMENT_TAG);

        if (fragment != null) {
            ((InfoUpdatable) fragment).updateInfo(task, pos);
        } else {
            InfoFragment infoFragment = InfoFragment.newInstance(task.getId(), pos);
            infoFragment.show(manager, INFO_FRAGMENT_TAG);
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

            @ColorInt int tint;

            if (isSorting) {
                Repo.getTasksOrderedByState(tasksAdapter::submitList);
                tint = R.color.colorPrimaryDark;
            } else {
                resetData();
                tint = Color.WHITE;
            }

            Drawable wrappedDrawable = DrawableCompat.wrap(item.getIcon());
            DrawableCompat.setTint(wrappedDrawable, tint);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateItem(UserTask task, int pos) {
        resetData();
    }

    @Override
    public void onDelete(UserTask task, int pos) {
        resetData();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(INFO_FRAGMENT_TAG);
        if (fragment != null) {
            manager.beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Repo.getFilteredTasks(newText, tasksAdapter::submitList);
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
        Repo.getTasks(tasksAdapter::submitList);
    }

    @Override
    public void onSwiped(int position) {
        UserTask task = tasksAdapter.getCurrentList().get(position);
        Repo.deleteTask(task, () -> {
            resetData();
            showUndoSnackbar(task, position);
        });
    }

    private void showUndoSnackbar(UserTask task, int position) {
        View view = findViewById(R.id.main_layout);

        String msg = getString(R.string.task_deleted, task.getDetails());

        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Undo delete", v -> undoDelete(task, position))
                .show();
    }

    private void undoDelete(UserTask task, int position) {
        List<UserTask> userTasks = new ArrayList<>(tasksAdapter.getCurrentList());
        userTasks.add(position, task);
        tasksAdapter.submitList(userTasks);

        Repo.insertTask(task, () -> {
            toast("Restored");
        });
    }
}
