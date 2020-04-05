package com.lineate.timeconverter.entity;

import java.util.List;

/**
 * This class is wrapper for user's lists of cities
 */
public class UserListCities {
    /**
     * currentHourMainCity - quantity hour in main city now
     */
    public int currentHourMainCity;
    /**
     * listCities - list of data for city about offset relative main city
     */
    public List<LocationTimeAdjustment> listCities;

    public UserListCities() {
    }
}
