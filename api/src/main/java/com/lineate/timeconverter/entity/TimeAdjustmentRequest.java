package com.lineate.timeconverter.entity;

/**
 * This class need to transfer info about user's list of cities and  type of time format
 */
public class TimeAdjustmentRequest {

    /**
     * citiesList - list of user's cities
     */
    private CitiesListEntity citiesList;

    /**
     * timeFormat - string representation of time format
     */
    private TimeFormat timeFormat;

    public TimeAdjustmentRequest() {
    }

    /**
     * Method return list of user's cities
     */
    public CitiesListEntity getCitiesList() {
        return citiesList;
    }

    /**
     * Method change list of user's cities
     *
     * @param citiesList - new value for citiesList
     */
    public void setCitiesList(CitiesListEntity citiesList) {
        this.citiesList = citiesList;
    }

    /**
     * Method return string representation of time format
     */
    public TimeFormat getTimeFormat() {
        return timeFormat;
    }

    /**
     * Method change string representation of time format
     *
     * @param timeFormat - new value for timeFormat
     */
    public void setTimeFormat(TimeFormat timeFormat) {
        this.timeFormat = timeFormat;
    }
}
