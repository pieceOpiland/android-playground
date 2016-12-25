package com.example.pie.android.model;

import com.google.gson.annotations.Expose;

public class TodoItem {

    /**
     * The Server ID
     */
    @Expose(serialize = false)
    private int id;

    /**
     * The Cient ID - Should never be sent to the server.
     */
    private long _id;

    @Expose
    private String task;

    @Expose
    private boolean done;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

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