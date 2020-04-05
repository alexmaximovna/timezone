package com.lineate.timeconverter.entity;

/*Class contains info about city (name,lat,lng)*/

public class CityCoord {
    private String name;
    private String lat;
    private String lng;

    public CityCoord() {
    }

    public CityCoord(String name, String lat, String lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Method return name
     */
    public String getName() {
        return name;
    }
    /**
     * Method change name of city
     *
     * @param name - new name for city
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method return lat
     */
    public String getLat() {
        return lat;
    }
    /**
     * Method change lat
     *
     * @param lat - new value lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Method return lng
     */
    public String getLng() {
        return lng;
    }
    /**
     * Method change lng
     *
     * @param lng - new value for lng
     */
    public void setLng(String lng) {
        this.lng = lng;
    }
}
