package com.example.first_task_k__r__o__s__h.MainActivity;



public class RunnableWithObject<T> implements Runnable {
    private T object;
    public Runnable init(T object){
        this.object = object;
        return this;
    }

    @Override
    public void run() {
    }

    protected T getDescription() {
        return object;
    }

}
