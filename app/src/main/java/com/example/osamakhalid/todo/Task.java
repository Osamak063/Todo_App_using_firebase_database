package com.example.osamakhalid.todo;

/**
 * Created by Osama Khalid on 7/11/2017.
 */

public class Task {
    String taskname;
    String key;
    public Task(){}
    public Task(String TaskName){
        this.taskname=TaskName;
    }
    public String gettaskname(){
        return taskname;
    }
    public void setKey(String key){
        this.key=key;
    }

    public String getKey() {
        return key;
    }
}
