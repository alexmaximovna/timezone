package com.lineate.timeconverter.entity;

/*Class contains info about city from file*/
public class CityInfo  {
    private String country;
    private String value;
    private String lat;
    private String lng;

    public CityInfo() {
    }

    /**
     * Method return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method change name of country
     *
     * @param country - new value for country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method return value
     */
    public String getValue() {
        return value;
    }
    /**
     * Method change value
     *
     * @param value - new value
     */
    public void setValue(String value) {
        this.value = value;
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
     * @param lat - new value for lat
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
