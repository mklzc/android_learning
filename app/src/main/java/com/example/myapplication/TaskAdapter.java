package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> tasks;
    private final TaskDatabaseHelper dbHelper;
    public TaskAdapter(List<Task> tasks, TaskDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.cbCompleted.setChecked(task.isCompleted());

        holder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            dbHelper.updateTaskCompletion(task.getId(), isChecked);
        });

        holder.itemView.setOnLongClickListener(v -> {
            int taskId = task.getId();
            tasks.remove(position);
            notifyItemRemoved(position);
            dbHelper.deleteTask(taskId);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static public class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView tvTaskTitle;
            CheckBox cbCompleted;

        TaskViewHolder(View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
        }
    }
}
