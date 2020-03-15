package com.erank.tasks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateEditActivity extends AppCompatActivity {

    private EditText descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        descriptionET = findViewById(R.id.desc_et);

        Button createBtn = findViewById(R.id.create_btn);
        createBtn.setOnClickListener(view -> createTask());
    }

    private void createTask() {
        String desc = descriptionET.getText().toString();
        if (desc.isEmpty()) {
            descriptionET.setError("Please fill something");
            return;
        }
        descriptionET.setError(null);

        UserTask userTask = new UserTask(desc);
        DataManger.getInstance().addTask(userTask);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
