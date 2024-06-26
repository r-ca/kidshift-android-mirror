package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskDataImpl implements TaskData {

    KSActions ksActions;

    @Inject
    public TaskDataImpl(KSActions ksActions) {
        this.ksActions = ksActions;
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks() {
        return CompletableFuture.supplyAsync(() -> {
            TaskListResponse data = ksActions.syncTasks().join();
            return data.getList().stream().map(task -> {
                // Convert TaskItemModel
                TaskItemModel model = new TaskItemModel();
                model.setInternalId(task.getId());
                model.setDisplayName(task.getName());
                model.setReward(task.getReward());

                return model;
            }).collect(Collectors.toList());
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
    public TaskItemModel getTask(String taskId) {
        return null;
    }

    @Override
    public void recordTaskCompletion(String taskId, String childId) {

    }
}
