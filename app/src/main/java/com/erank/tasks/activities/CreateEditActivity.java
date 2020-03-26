package com.erank.tasks.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.erank.tasks.R;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.DataManger;

import static android.R.layout.simple_spinner_dropdown_item;

public class CreateEditActivity extends AppCompatActivity {

    private EditText descriptionET;
    private Spinner stateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        descriptionET = findViewById(R.id.desc_et);
        stateSpinner = findViewById(R.id.state_spinner);

        Button createBtn = findViewById(R.id.create_btn);
        createBtn.setOnClickListener(view -> createTask());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, simple_spinner_dropdown_item);

        for (TaskState state : TaskState.values()) {
            arrayAdapter.add(state.capitalizedName());
        }

        stateSpinner.setAdapter(arrayAdapter);
        stateSpinner.setSelection(1);//Set to ready
    }

    private void createTask() {
        String desc = descriptionET.getText().toString();
        if (desc.isEmpty()) {
            descriptionET.setError("Please fill something");
            return;
        }
        descriptionET.setError(null);

        int selectedItem = stateSpinner.getSelectedItemPosition();
        TaskState state = TaskState.values()[selectedItem];
        addTaskAndClose(desc, state);
    }

    private void addTaskAndClose(String desc, TaskState state) {

        UserTask userTask = new UserTask(desc, state);
        DataManger.getInstance().addTask(userTask);

        Runnable finishAction = () -> {
            setResult(RESULT_OK);
            finish();
        };

        new AlertDialog.Builder(this)
                .setTitle("Good news")
                .setMessage("Added successfully!")
                .setPositiveButton("OK", (d, i) -> finishAction.run())
                .setOnDismissListener(d -> finishAction.run())
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
