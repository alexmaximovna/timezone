package com.lineate.timeconverter.entity;

/**
 * This class need to keep data about user's lists of cities
 */
public class AdjustmentList {
    /**
     * id - identifier of list
     */
    public String id;

    /**
     * name - custom name of list
     */
    public String name;

    public AdjustmentList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AdjustmentList() {
    }

}
