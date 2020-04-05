package com.lineate.timeconverter.entity;

public class CurrentTimeList {

    private CityCurrTime [] currentTime;

    public CurrentTimeList() {
    }

    public CurrentTimeList(CityCurrTime[] currentTime) {
        this.currentTime = currentTime;
    }


    /**
     * Method return array of current time and name city
     */
    public CityCurrTime[] getCurrentTime() {
        return currentTime;
    }

    /**
     * Method change array of current time name city
     *
     * @param currentTime - new value for current time
     */
    public void setCurrentTime(CityCurrTime[] currentTime) {
        this.currentTime = currentTime;
    }
}
