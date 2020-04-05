package com.lineate.timeconverter.entity;


import java.util.List;

/**
 * This class need to contains full data about list of cities
 */
public class CitiesListEntity {
    /**
     * id - identifier of list
     */
    public String id;
    /**
     * name - custom name of list
     */
    public String name;
    /**
     * list of cities in user list
     */
    public List<City> data;
    /**
     * name of city relative to which the offset is
     */
    public String currentMainCity;


    public CitiesListEntity() {
    }


}
