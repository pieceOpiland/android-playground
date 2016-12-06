package com.example.pie.android.models;

import com.google.gson.annotations.Expose;

public class TodoItem {

    // I don't think this actually does anything with converter-gson.
    // see: https://static.javadoc.io/com.google.code.gson/gson/2.6.2/com/google/gson/GsonBuilder.html#excludeFieldsWithoutExposeAnnotation--
    @Expose(serialize = false)
    private int id;

    private String task;
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void complete(){
        this.done = true;
    }
}