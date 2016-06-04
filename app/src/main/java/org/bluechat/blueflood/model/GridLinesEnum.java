package org.bluechat.blueflood.model;

public enum GridLinesEnum {

    NONE   (0, "pref.gridLines.none.txt"),
    ALL    (1, "pref.gridLines.all.txt"),
    COLORS (2, "pref.gridLines.colors.txt");

    public final int intValue;
    public final String l10nKey;

    private GridLinesEnum(final int intValue, final String l10nKey) {
        this.intValue = intValue;
        this.l10nKey = l10nKey; //L10N = Localization
    }

    /**
     * get the GridLinesEnum for the specified intValue,
     * or null if none was found.
     * @param intValue
     * @return
     */
    public static GridLinesEnum valueOf(final int intValue) {
        GridLinesEnum result = null;
        for (final GridLinesEnum gle : values()) {
            if (gle.intValue == intValue) {
                result = gle;
                break;
            }
        }
        return result;
    }
}
