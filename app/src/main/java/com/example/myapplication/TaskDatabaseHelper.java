package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 2;

    // 数据表和字段
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_COMPLETED = "completed";
    private static final String COLUMN_DEADLINE = "deadline";
    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_COMPLETED + " INTEGER, "
                + COLUMN_DEADLINE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE tasks ADD COLUMN deadline INTEGER"); // 添加新列
        }
    }

    // 插入任务
    public long addTask(String title, boolean completed, Date deadline) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);
        values.put(COLUMN_DEADLINE, deadline != null ? deadline.getTime() : null);
        return db.insert(TABLE_NAME, null, values);
    }

    // 查询所有任务
    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COMPLETED)) == 1;
            long deadlineMillis = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE));
            Date deadline = deadlineMillis != 0 ? new Date(deadlineMillis) : null;

            tasks.add(new Task(id, title, completed, deadline));
        }
        cursor.close();
        for (Task task : tasks) {
            Log.d("asasass", task.getTitle() + " - " + task.getDeadline());
        }
        if (!tasks.isEmpty()) {
            tasks.sort(new Comparator<Task>() {
                @Override
                public int compare(Task task1, Task task2) {
                    if (task1.getDeadline() == null && task2.getDeadline() == null) {
                        return 0;  // 两者 deadline 都为 null，认为相等
                    } else if (task1.getDeadline() == null) {
                        return 1;  // task1 的 deadline 为 null，排到最后
                    } else if (task2.getDeadline() == null) {
                        return -1; // task2 的 deadline 为 null，排到最后
                    }
                    return task1.getDeadline().compareTo(task2.getDeadline());
                }
            });
        }
        return tasks;
    }

    // 更新任务完成状态
    public void updateTaskCompletion(int id, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // 删除任务
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}

