package com.lineate.timeconverter.entity;

public class TimeResponseDto {
    private Long hours;
    private Long minutes;
    private Long seconds;

    public TimeResponseDto() {
    }

    public TimeResponseDto(Long hours, Long minutes, Long seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
    /**
     * Method return hours
     */
    public Long getHours() {
        return hours;
    }
    /**
     * Method change hours
     *
     * @param hours - new value for hours
     */
    public void setHours(Long hours) {
        this.hours = hours;
    }
    /**
     * Method return minutes
     */
    public Long getMinutes() {
        return minutes;
    }
    /**
     * Method change minutes
     *
     * @param minutes - new value for minutes
     */
    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }
    /**
     * Method return seconds
     */
    public Long getSeconds() {
        return seconds;
    }
    /**
     * Method change seconds
     *
     * @param seconds- new value for seconds
     */
    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

}
