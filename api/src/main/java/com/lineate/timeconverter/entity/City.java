package com.lineate.timeconverter.entity;


/**
 * This class need to contains  data about City
 */
public class City {
    /**
     * name - name of city
     */
    private String name;
    /**
     * timezone - timezone for this city
     */
    private String timezone;
    /**
     * offset - offset relative to the main city
     */
    private String offset;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public City(String nameCity, String tz) {
        name = nameCity;
        timezone = tz;
    }

    public City(String name, String timezone, String offset) {
        this.name = name;
        this.timezone = timezone;
        this.offset = offset;
    }


    /**
     * Method return offset relative to the main city for this city
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Method change offset relative to the main city for this city
     *
     * @param offset - new value for offset
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * Method return name of this city
     */
    public String getName() {
        return name;
    }

    /**
     * Method change name for this city
     *
     * @param name - new name for city
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method return name of this timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Method change name for this timezone
     *
     *
     * @param timezone - new value for timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
