package one.nem.kidshift.data.room.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.room.KidShiftDatabase;
import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.data.room.entity.TaskCacheEntity;
import one.nem.kidshift.data.room.entity.TaskChildLinkageEntity;
import one.nem.kidshift.data.room.utils.converter.ChildCacheConverter;
import one.nem.kidshift.data.room.utils.converter.TaskCacheConverter;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@Module
@InstallIn(SingletonComponent.class)
public class CacheWrapper {

    private KidShiftDatabase kidShiftDatabase;
    private KSLogger logger;

    @Inject
    public CacheWrapper(KidShiftDatabase kidShiftDatabase, KSLoggerFactory loggerFactory) {
        this.kidShiftDatabase = kidShiftDatabase;
        this.logger = loggerFactory.create("CacheWrapper");
    }

    /**
     * キャッシュを更新する
     * @param childList 子供リスト
     * @param taskList タスクリスト
     * @return CompletableFuture
     */
    public CompletableFuture<Void> updateChildTaskCache(List<ChildModel> childList, List<TaskItemModel> taskList) {
        return CompletableFuture.runAsync(() -> {
            logger.debug("Updating cache");
            insertChildList(childList).join();
            logger.info("Child list inserted");
            insertTaskList(taskList).join();
            logger.info("Task list inserted");

            // Update Linkage
            List<TaskChildLinkageEntity> linkageList = new ArrayList<>(); // TODO-rca: タスク or 子供が追加された場合だけ実行するようにする?
            for (TaskItemModel task : taskList) {
                if (task.getAttachedChildren() == null || task.getAttachedChildren().isEmpty()) {
                    logger.warn("Task " + task.getName() + " has no attached children");
                    continue;
                }
                task.getAttachedChildren().forEach(child -> {
                    TaskChildLinkageEntity linkage = new TaskChildLinkageEntity();
                    linkage.taskId = task.getId();
                    linkage.childId = child.getId();
                    linkageList.add(linkage);
                });
            }
            kidShiftDatabase.taskChildLinkageDao().insertAll(linkageList);
        });
    }

    /**
     * 子供リストをDBに挿入する
     * @param childList 子供リスト
     * @return CompletableFuture
     */
    private CompletableFuture<Void> insertChildList(List<ChildModel> childList) {
        return CompletableFuture.runAsync(() -> {
            kidShiftDatabase.childCacheDao().insertChildList(ChildCacheConverter.childModelListToChildCacheEntityList(childList));
        });
    }

    /**
     * タスクリストをDBに挿入する
     * @param taskList タスクリスト
     * @return CompletableFuture
     */
    private CompletableFuture<Void> insertTaskList(List<TaskItemModel> taskList) {
        return CompletableFuture.runAsync(() -> {
            kidShiftDatabase.taskCacheDao().insertTaskList(TaskCacheConverter.taskModelListToTaskCacheEntityList(taskList));
        });
    }

    /**
     * 子供リストを取得する
     * @return CompletableFuture
     */
    public CompletableFuture<List<ChildModel>> getChildList() {
        return CompletableFuture.supplyAsync(() -> {
            List<ChildCacheEntity> result = kidShiftDatabase.childCacheDao().getChildList();
            return ChildCacheConverter.childCacheEntityListToChildModelList(result);
        });
    }

    /**
     * タスクリストを取得する
     * @return CompletableFuture
     */
    public CompletableFuture<List<TaskItemModel>> getTaskList() {
        return CompletableFuture.supplyAsync(() -> {
            List<TaskCacheEntity> result = kidShiftDatabase.taskCacheDao().getTaskList();
            return TaskCacheConverter.taskCacheEntityListToTaskModelList(result);
        });
    }


}
