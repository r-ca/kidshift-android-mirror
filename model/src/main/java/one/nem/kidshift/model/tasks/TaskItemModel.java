package one.nem.kidshift.model.tasks;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.List;

import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.tasks.condition.TaskConditionBaseModel;

public class TaskItemModel {

    private String id;
    private String name;
    private String iconEmoji; // Optional
    private String bgColor;   // Optional
    private int reward;
    private List<ChildModel> attachedChildren; // Optional

    // コンストラクタ
    // 全プロパティ
    public TaskItemModel(String id, String name, String iconEmoji, String bgColor, int reward, List<ChildModel> attachedChildren) {
        this.id = id;
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
        this.attachedChildren = attachedChildren;
    }

    // IDなし (登録時など)
    public TaskItemModel(String name, String iconEmoji, String bgColor, int reward, List<ChildModel> attachedChildren) {
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
        this.attachedChildren = attachedChildren;
    }

    // Optionalなフィールドなし
    public TaskItemModel(String id, String name, int reward, List<ChildModel> attachedChildren) {
        this.id = id;
        this.name = name;
        this.reward = reward;
        this.attachedChildren = attachedChildren;
    }

    // ID, Optionalなフィールドなし (登録時など)
    public TaskItemModel(String name, int reward, List<ChildModel> attachedChildren) {
        this.name = name;
        this.reward = reward;
        this.attachedChildren = attachedChildren;
    }

    // 空
    public TaskItemModel() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getBgColorInt() {
        return Color.parseColor(bgColor);
    }

    public void setBgColorInt(int color) {
        this.bgColor = String.format("#%06X", 0xFFFFFF & color);
    }

    public List<ChildModel> getAttachedChildren() {
        return attachedChildren;
    }

    public void setAttachedChildren(List<ChildModel> attachedChildren) {
        this.attachedChildren = attachedChildren;
    }
}
