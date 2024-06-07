package one.nem.kidshift.model.enums;

public enum WeekdayEnum {

    MONDAY("月曜日"),
    TUESDAY("火曜日"),
    WEDNESDAY("水曜日"),
    THURSDAY("木曜日"),
    FRIDAY("金曜日"),
    SATURDAY("土曜日"),
    SUNDAY("日曜日");

    private final String displayName;

    private WeekdayEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static WeekdayEnum fromDisplayName(String displayName) {
        for (WeekdayEnum value : WeekdayEnum.values()) {
            if (value.getDisplayName().equals(displayName)) {
                return value;
            }
        }
        return null;
    }

    public static WeekdayEnum fromOrdinal(int ordinal) {
        for (WeekdayEnum value : WeekdayEnum.values()) {
            if (value.ordinal() == ordinal) {
                return value;
            }
        }
        return null;
    }
}
