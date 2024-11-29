package com.example.myapplication;

import java.util.Date;

public class Task {
    private final int id;
    private String title;
    private boolean isCompleted;
    private Date deadline;

    public Task(int id, String title, Boolean isCompleted, Date deadline) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDeadline() { return deadline; }

    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
