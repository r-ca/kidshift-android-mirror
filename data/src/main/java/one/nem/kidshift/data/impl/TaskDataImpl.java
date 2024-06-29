package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.retrofit.model.converter.TaskModelConverter;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskDataImpl implements TaskData {

    KSActions ksActions;
    private CacheWrapper cacheWrapper;

    @Inject
    public TaskDataImpl(KSActions ksActions, CacheWrapper cacheWrapper) {
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks() {
        return CompletableFuture.supplyAsync(() -> {
            return null; // TODO-rca: 実装
        });
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks(String childId) {
        return null;
    }

    @Override
    public void addTask(TaskItemModel task) {

    }

    @Override
    public void removeTask(String taskId) {

    }

    @Override
    public void updateTask(TaskItemModel task) {

    }

    @Override
    public CompletableFuture<TaskItemModel> getTask(String taskId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void recordTaskCompletion(String taskId, String childId) {

    }
}
