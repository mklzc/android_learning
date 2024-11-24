package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ToDoList extends AppCompatActivity {

    private EditText etTask;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        etTask = findViewById(R.id.etTask);
        Button btnAddTask = findViewById(R.id.btnAddTask);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        dbHelper = new TaskDatabaseHelper(this);

        taskList = new ArrayList<>();
        loadTasksFromDatabase();
        taskAdapter = new TaskAdapter(taskList, dbHelper);

        rvTasks.setLayoutManager(new LinearLayoutManager(ToDoList.this));
        rvTasks.setAdapter(taskAdapter);


        btnAddTask.setOnClickListener(v -> {
            String taskTitle = etTask.getText().toString().trim();

            if (TextUtils.isEmpty(taskTitle)) {
                etTask.setError("任务不能为空");
                return;
            }

            long id = dbHelper.addTask(taskTitle, false);
            taskList.add(new Task((int) id, taskTitle, false));
            taskAdapter.notifyItemInserted(taskList.size() - 1);
            etTask.setText("");
        });
    }
    private void loadTasksFromDatabase() {
        Cursor cursor = dbHelper.getAllTasks();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed")) == 1;
            taskList.add(new Task(id, title, completed));
        }
        cursor.close();
    }
}