package one.nem.kidshift.data.retrofit.model.task;

public class TaskBaseItem {
    private String id;
    private String name;
    private String iconEmoji; // Optional
    private String bgColor;   // Optional
    private int reward;

    /**
     * コンストラクタ (全プロパティ)
     * @param id タスクID
     * @param name タスク名
     * @param iconEmoji アイコン絵文字
     * @param bgColor 背景色
     * @param reward 報酬
     */
    public TaskBaseItem(String id, String name, String iconEmoji, String bgColor, int reward) {
        this.id = id;
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
    }

    /**
     * コンストラクタ (IDなし)
     * @param name タスク名
     * @param iconEmoji アイコン絵文字
     * @param bgColor 背景色
     * @param reward 報酬
     */
    public TaskBaseItem(String name, String iconEmoji, String bgColor, int reward) {
        this.name = name;
        this.iconEmoji = iconEmoji;
        this.bgColor = bgColor;
        this.reward = reward;
    }

    /**
     * コンストラクタ (Optionalなフィールドなし)
     * @param id タスクID
     * @param name タスク名
     * @param reward 報酬
     */
    public TaskBaseItem(String id, String name, int reward) {
        this.id = id;
        this.name = name;
        this.reward = reward;
    }

    /**
     * コンストラクタ (ID, Optionalなフィールドなし)
     * @param name タスク名
     * @param reward 報酬
     */
    public TaskBaseItem(String name, int reward) {
        this.name = name;
        this.reward = reward;
    }

    /**
     * コンストラクタ (空)
     */
    public TaskBaseItem() {
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
}
