package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

public class TaskAddRequest extends TaskBaseItem {
    private List<String> attachedChildren;

    // コンストラクタ
    // 全プロパティ

    /**
     * コンストラクタ (全プロパティ)
     * @param name タスク名
     * @param iconEmoji アイコン絵文字
     * @param bgColor 背景色
     * @param reward 報酬
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskAddRequest(String name, String iconEmoji, String bgColor, int reward, List<String> attachedChildren) {
        super(name, iconEmoji, bgColor, reward);
        this.attachedChildren = attachedChildren;
    }

    // ID, Optionalなフィールドなし (登録時など)
    /**
     * コンストラクタ (ID, Optionalなフィールドなし)
     * @param name タスク名
     * @param reward 報酬
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskAddRequest(String name, int reward, List<String> attachedChildren) {
        super(name, reward);
        this.attachedChildren = attachedChildren;
    }

    // 空
    /**
     * コンストラクタ (空)
     */
    public TaskAddRequest() {
    }

    // baseItemを指定して拡張
    /**
     * コンストラクタ (baseItemを指定して拡張)
     * @param taskBaseItem タスクベースアイテム
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskAddRequest(TaskBaseItem taskBaseItem, List<String> attachedChildren) {
        super(taskBaseItem.getName(), taskBaseItem.getIconEmoji(), taskBaseItem.getBgColor(), taskBaseItem.getReward());
        this.attachedChildren = attachedChildren;
    }

    // Getters and setters
    public List<String> getAttachedChildren() {
        return attachedChildren;
    }

    public void setAttachedChildren(List<String> attachedChildren) {
        this.attachedChildren = attachedChildren;
    }
}