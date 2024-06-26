package one.nem.kidshift.data.impl;

import java.util.List;

import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskDataImpl implements TaskData {
    @Override
    public List<TaskItemModel> getTasks() {
        return null;
    }

    @Override
    public List<TaskItemModel> getTasks(String childId) {
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
