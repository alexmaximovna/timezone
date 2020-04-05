package com.lineate.timeconverter.entity;

/**
 * Class contains name of city and its current time
 */
public class CityCurrTime {
    private String name;
    private String currTime;

    public CityCurrTime() {
    }

    public CityCurrTime(String name, String currTime) {
        this.name = name;
        this.currTime = currTime;
    }
    /**
     * Method return  name city
     */
    public String getName() {
        return name;
    }
    /**
     * Method change  name city
     *
     * @param name - new value for city
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method return  current time
     */
    public String getCurrTime() {
        return currTime;
    }
    /**
     * Method change  of current time
     *
     * @param currTime - new value for current time
     */
    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }
}
