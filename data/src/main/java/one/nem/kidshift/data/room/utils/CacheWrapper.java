package one.nem.kidshift.data.room.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.room.KidShiftDatabase;
import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.data.room.entity.TaskCacheEntity;
import one.nem.kidshift.data.room.utils.converter.ChildCacheConverter;
import one.nem.kidshift.data.room.utils.converter.TaskCacheConverter;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;

@Module
@InstallIn(SingletonComponent.class)
public class CacheWrapper {

    private KidShiftDatabase kidShiftDatabase;
    private KSLogger logger;

    @Inject
    public CacheWrapper(KidShiftDatabase kidShiftDatabase, KSLogger logger) {
        this.kidShiftDatabase = kidShiftDatabase;
        this.logger = logger;
    }

    public CompletableFuture<Void> updateCache(List<ChildModel> childList, List<TaskItemModel> taskList) {
        return CompletableFuture.runAsync(() -> {
            logger.debug("Updating cache");
            insertChildList(childList).join();
            logger.info("Child list inserted");
            insertTaskList(taskList).join();
            logger.info("Task list inserted");

            // Update Linkage
            // TODO
        });
    }

    private CompletableFuture<Void> insertChildList(List<ChildModel> childList) {
        return CompletableFuture.runAsync(() -> {
            kidShiftDatabase.childCacheDao().insertChildList(ChildCacheConverter.childModelListToChildCacheEntityList(childList));
        });
    }

    private CompletableFuture<Void> insertTaskList(List<TaskItemModel> taskList) {
        return CompletableFuture.runAsync(() -> {
            kidShiftDatabase.taskCacheDao().insertTaskList(TaskCacheConverter.taskModelListToTaskCacheEntityList(taskList));
        });
    }

    public CompletableFuture<List<ChildModel>> getChildList() {
        return CompletableFuture.supplyAsync(() -> {
            // Get a list of children from the database
            return null;
        });
    }

    public CompletableFuture<List<TaskItemModel>> getTaskList() {
        return CompletableFuture.supplyAsync(() -> {
            // Get a list of tasks from the database
            return null;
        });
    }


}
