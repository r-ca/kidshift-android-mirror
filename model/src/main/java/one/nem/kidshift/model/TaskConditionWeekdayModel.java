package one.nem.kidshift.model;

import one.nem.kidshift.model.enums.WeekdayEnum;

public class TaskConditionWeekdayModel extends TaskConditionBaseModel {

    WeekdayEnum weekday;

    // constructor
    public TaskConditionWeekdayModel(WeekdayEnum weekday) {
        this.weekday = weekday;
    }

    // getter
    public WeekdayEnum getWeekday() {
        return weekday;
    }

    // setter
    public void setWeekday(WeekdayEnum weekday) {
        this.weekday = weekday;
    }

}
