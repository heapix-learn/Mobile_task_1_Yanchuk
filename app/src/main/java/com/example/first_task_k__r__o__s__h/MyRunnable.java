package com.example.first_task_k__r__o__s__h;

public class MyRunnable implements Runnable {
    private AppContext.TypeOfAuthManagerError error;
    Runnable init(AppContext.TypeOfAuthManagerError myError){
        this.error=myError;
        return this;
    }

    @Override
    public void run() {
    }

    protected AppContext.TypeOfAuthManagerError getError() {
        return error;
    }

}
