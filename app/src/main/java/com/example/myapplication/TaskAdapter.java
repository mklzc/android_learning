package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        if (task.getDeadline() != null) {
            Date deadline = task.getDeadline();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String output = "Deadline: " + dateFormat.format(deadline);
            holder.tvTaskDeadline.setText(output);
        }
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
        TextView tvTaskDeadline;
        CheckBox cbCompleted;
        TaskViewHolder(View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDeadline = itemView.findViewById(R.id.tvDeadline);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
        }
    }
}
