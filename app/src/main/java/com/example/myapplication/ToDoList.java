package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ToDoList extends AppCompatActivity {

    private EditText etTask, etDeadline;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        etTask = findViewById(R.id.etTaskTitle);
        etDeadline = findViewById(R.id.etTaskDeadline);
        Button btnAddTask = findViewById(R.id.btnAddTask);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        dbHelper = new TaskDatabaseHelper(this);

        taskList = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(taskList, dbHelper);

        rvTasks.setLayoutManager(new LinearLayoutManager(ToDoList.this));
        rvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> {
            String taskTitle = etTask.getText().toString().trim();
            String deadlineText = etDeadline.getText().toString().trim();

            Date deadline = null;
            try {
                deadline = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(deadlineText);
            } catch (Exception ignored) {
            }

            if (TextUtils.isEmpty(taskTitle)) {
                etTask.setError("任务不能为空");
                return;
            }
            long id = dbHelper.addTask(taskTitle, false, deadline);
            taskList.add(new Task((int) id, taskTitle, false, deadline));
            taskAdapter.notifyItemInserted(taskList.size() - 1);
            etTask.setText("");
            etDeadline.setText("");
        });
    }
}