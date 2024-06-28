package one.nem.kidshift.model.tasks;

import android.graphics.Color;

import androidx.annotation.NonNull;

import one.nem.kidshift.model.tasks.condition.TaskConditionBaseModel;

public class TaskItemModel {

    private String id;
    private String name;
    private String iconEmoji; // Optional
    private String bgColor;   // Optional
    private int reward;

    // コンストラクタ
    // 全プロパティ
    public TaskItemModel(String id, String name, String iconEmoji, String bgColor, int reward) {
        this.id = id;
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
    }

    // IDなし (登録時など)
    public TaskItemModel(String name, String iconEmoji, String bgColor, int reward) {
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
    }

    // Optionalなフィールドなし
    public TaskItemModel(String id, String name, int reward) {
        this.id = id;
        this.name = name;
        this.reward = reward;
    }

    // ID, Optionalなフィールドなし (登録時など)
    public TaskItemModel(String name, int reward) {
        this.name = name;
        this.reward = reward;
    }

    // 空
    public TaskItemModel() {
    }
}
