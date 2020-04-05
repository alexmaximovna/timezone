package com.lineate.timeconverter.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class need to keep type of time format
 */
@JsonDeserialize(using = TimeFormatDeserializer.class)
public enum TimeFormat {
    TIME_FORMAT_24_HOURS("24h"),
    TIME_FORMAT_AM_PM("AM/PM");

    /**
     * timeFormat - string representation of time format
     */
    public String timeFormat;

    /**
     * Method return string representation of time format
     *
     * @param timeFormat - new value for timeFormat
     */
    TimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * Method return TimeFormat by its string value
     *
     * @param value - string value of timeFormat
     * @return object of type TimeFormat
     */
    @JsonCreator
    public static TimeFormat fromText(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (TimeFormat timeFormat : values()) {
            if (value.equals(timeFormat.getTimeFormat())) {
                return timeFormat;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Method return object of type TimeFormat in String format
     *
     * @return object of type TimeFormat in String format
     */
    public String getTimeFormat() {
        return timeFormat;
    }


}
