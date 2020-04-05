package com.lineate.timeconverter.entity;

/**
 * This class need to keep partial timezone
 */
public enum PartialTimeZone {
    ASIA_KABUL("Asia/Kabul"),
    AUSTRALIA_ADELAIDE("Australia/Adelaide"),
    AUSTRALIA_BROKEN_HILL("Australia/Broken_Hill"),
    AUSTRALIA_DARWIN("Australia/Darwin"),
    AMERICA_ST_JOHNS("America/St_Johns"),
    INDIAN_COCOS("Indian/Cocos"),
    Pacific_MARQUESAS("Pacific/Marquesas"),
    ASIA_KOLKATA("Asia/Kolkata"),
    ASIA_TEHRAN("Asia/Tehran"),
    ASIA_YANGON("Asia/Yangon"),
    ASIA_COLOMBO("Asia/Colombo");


    /**
     * timeZone - string representation of partial timezone
     */
    private String timeZone;

    PartialTimeZone(String timeZone) {
        this.timeZone = timeZone;

    }

    /**
     * Method return string representation of partial timezone
     *
     * @return string representation of partial timezone
     */
    public String getTimeZone() {
        return timeZone;
    }
}
