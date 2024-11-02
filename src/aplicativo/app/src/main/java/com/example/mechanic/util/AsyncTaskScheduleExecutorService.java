package com.example.mechanic.util;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AsyncTaskScheduleExecutorService < Params, Progress, Result > {

    private ScheduledExecutorService scheduledExecutor;
    private ExecutorService executor;
    private long delayMillis;
    private boolean isRepeating;
    private Handler handler;

    protected AsyncTaskScheduleExecutorService() {
        executor = Executors.newSingleThreadExecutor(r1 -> {
            Thread thread = new Thread(r1);
            thread.setDaemon(true);
            return thread;
        });
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        delayMillis = 30000; // 30 seconds
        isRepeating = false;
    }

    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public Handler getHandler() {
        if (handler == null) {
            synchronized(AsyncTaskExecutorService.class) {
                handler = new Handler(Looper.getMainLooper());
            }
        }
        return handler;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    public void setDelayMillis(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    public void startPeriodicExecution(){startPeriodicExecution(null);}

    public void startPeriodicExecution(Params params) {
        getHandler().post(() -> {
            onPreExecute();
            scheduledExecutor.scheduleAtFixedRate(() -> {
                Result result = doInBackground(params); // Você pode ajustar os parâmetros conforme necessário
                getHandler().post(() -> onPostExecute(result));
            }, 0, delayMillis, TimeUnit.MILLISECONDS);
        });
    }

    // Método para parar a execução periódica
    public void stopPeriodicExecution() {
        scheduledExecutor.shutdownNow();
    }

    protected void onPreExecute() {
        // Override this method whereever you want to perform task before background execution get started
    }

    protected abstract Result doInBackground(Params params);

    protected abstract void onPostExecute(Result result);

    protected void onProgressUpdate(@NotNull Progress value) {
        // Override this method whereever you want update a progress result
    }

    // used for push progress resport to UI
    public void publishProgress(@NotNull Progress value) {
        getHandler().post(() -> onProgressUpdate(value));
    }

    public void execute() {
        execute(null);
    }

    public void execute(Params params) {
        getHandler().post(() -> {
            onPreExecute();
            executor.execute(() -> {
                Result result = doInBackground(params);
                getHandler().post(() -> onPostExecute(result));
            });
        });
    }

    public void shutDown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public boolean isCancelled() {
        return executor == null || executor.isTerminated() || executor.isShutdown();
    }
}
