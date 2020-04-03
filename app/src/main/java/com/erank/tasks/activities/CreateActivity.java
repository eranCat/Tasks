package com.erank.tasks.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.erank.tasks.R;
import com.erank.tasks.models.TaskState;
import com.erank.tasks.models.UserTask;
import com.erank.tasks.utils.customViews.TaskStatesSpinner;
import com.erank.tasks.utils.room.Repo;

public class CreateActivity extends AppCompatActivity {

    private EditText descriptionET;
    private TaskStatesSpinner stateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        descriptionET = findViewById(R.id.desc_et);
        stateSpinner = findViewById(R.id.state_spinner);

        Button createBtn = findViewById(R.id.create_btn);
        createBtn.setOnClickListener(v -> createTask());
    }

    private void createTask() {
        String desc = descriptionET.getText().toString();
        if (desc.isEmpty()) {
            descriptionET.setError("Please fill something");
            return;
        }

        descriptionET.setError(null);

        TaskState state = stateSpinner.getSelectedState();
        addTaskAndClose(desc, state);
    }

    private void addTaskAndClose(String desc, TaskState state) {

        Repo.insertTask(new UserTask(desc, state), () -> {
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
