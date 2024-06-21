package one.nem.kidshift.data.impl;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.model.tasks.condition.TaskConditionBaseModel;
import one.nem.kidshift.model.tasks.condition.TaskConditionNoneModel;
import one.nem.kidshift.utils.KSLogger;

public class TaskDataDummyImpl implements TaskData {

    private Faker faker;

    @Inject
    KSLogger logger;

    @Inject
    public TaskDataDummyImpl() {
        faker = new Faker();
//        logger.setTag("TaskDataDummyImpl");
    }

    @Override
    public List<TaskItemModel> getTasks() {
//        logger.info("getTotalReward called");
        List<TaskItemModel> tasks = new ArrayList<>();
        int totalTasks = faker.number().numberBetween(1, 15);
//        logger.info("Returning total tasks: " + totalTasks);
        for (int i = 0; i < totalTasks; i++) {
            tasks.add(new TaskItemModel(
                    UUID.randomUUID().toString(),
                    faker.lorem().sentence(), UUID.randomUUID().toString(),
                    new TaskConditionNoneModel(),
                    faker.number().numberBetween(1, 1000)));
        }
//        logger.info("Returning tasks: " + tasks);
        return tasks;
    }

    @Override
    public void addTask(TaskItemModel task) {
        logger.info("addTask called");
        logger.info("Task: " + task);
    }

    @Override
    public void removeTask(String taskId) {
        logger.info("removeTask called");
        logger.info("Task ID: " + taskId);
    }

    @Override
    public void updateTask(TaskItemModel task) {
        logger.info("updateTask called");
        logger.info("Task: " + task);
    }

    @Override
    public TaskItemModel getTask(String taskId) {
        List<TaskItemModel> tasks = getTasks();
        // return random task
        return tasks.get(faker.number().numberBetween(0, tasks.size()));
    }

    @Override
    public void recordTaskCompletion(String taskId, String childId) {
        logger.info("recordTaskCompletion called");
        logger.info("Task ID: " + taskId);
        logger.info("Child ID: " + childId);
    }
}
