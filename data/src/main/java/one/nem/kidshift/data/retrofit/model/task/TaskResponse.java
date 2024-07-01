package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

public class TaskResponse extends TaskBaseItem {
    private List<String> attachedChildren;

    // コンストラクタ
    // 全プロパティ
    /**
     * コンストラクタ (全プロパティ)
     * @param id タスクID
     * @param name タスク名
     * @param iconEmoji アイコン絵文字
     * @param bgColor 背景色
     * @param reward 報酬
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskResponse(String id, String name, String iconEmoji, String bgColor, int reward, List<String> attachedChildren) {
        super(id, name, iconEmoji, bgColor, reward);
        this.attachedChildren = attachedChildren;
    }

    // 必須プロパティ
    /**
     * コンストラクタ (必須プロパティ)
     * @param id タスクID
     * @param name タスク名
     * @param reward 報酬
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskResponse(String id, String name, int reward, List<String> attachedChildren) {
        super(id, name, reward);
        this.attachedChildren = attachedChildren;
    }

    // 空
    /**
     * コンストラクタ (空)
     */
    public TaskResponse() {
    }

    // baseItemを指定して拡張
    /**
     * コンストラクタ (baseItemを指定して拡張)
     * @param taskBaseItem タスクベースアイテム
     * @param attachedChildren アタッチされた子供IDリスト
     */
    public TaskResponse(TaskBaseItem taskBaseItem, List<String> attachedChildren) {
        super(taskBaseItem.getId(), taskBaseItem.getName(), taskBaseItem.getIconEmoji(), taskBaseItem.getBgColor(), taskBaseItem.getReward());
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