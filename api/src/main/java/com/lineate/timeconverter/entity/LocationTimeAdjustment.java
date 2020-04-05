package com.lineate.timeconverter.entity;


/**
 * This class need to keep data for city about offset relative main city
 */
public class LocationTimeAdjustment {

    /**
     * locationName - name of city
     */
    private String locationName;

    /**
     * hoursAlignment - array of 24 hours with offset relative main city
     */
    private String[] hoursAlignment;

    /**
     * offsetFromMainCity - number of hours offset relative to the main city
     */
    private String offsetFromMainCity;



    public LocationTimeAdjustment() {
    }

    /**
     * Method return name of city
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Method change name of city
     *
     * @param locationName - new value for locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Method return lists of 24 hours with offset relative main city
     *
     * @return lists of 24 hours with offset relative main city
     */
    public String[] getHoursAlignment() {
        return hoursAlignment;
    }

    /**
     * Method change lists of 24 hours with offset relative main city
     *
     * @param hoursAlignment - new values for array hoursAlignment
     */
    public void setHoursAlignment(String[] hoursAlignment) {
        this.hoursAlignment = hoursAlignment;
    }

    /**
     * Method return number of hours offset relative to the main city
     *
     * @return number of hours offset relative to the main city
     */
    public String getOffsetFromMainCity() {
        return offsetFromMainCity;
    }


    /**
     * Method change number of hours offset relative to the main city
     *
     * @param offsetFromMainCity -new value for offsetFromMainCity
     */
    public void setOffsetFromMainCity(String offsetFromMainCity) {
        this.offsetFromMainCity = offsetFromMainCity;
    }

    private LocationTimeAdjustment(Builder builder) {
        this.locationName = builder.locationName;
        this.hoursAlignment = builder.hoursAlignment;
        this.offsetFromMainCity = builder.offsetFromMainCity;
    }

    public static Builder create() {
        return new Builder();
    }

    public static Builder create(LocationTimeAdjustment locationTimeAdjustment) {
        return new Builder(locationTimeAdjustment);
    }

    public static final class Builder {

        private String locationName;

        private String[] hoursAlignment;

        private String offsetFromMainCity;

        private Builder() {
        }

        private Builder(LocationTimeAdjustment locationTimeAdjustment) {
            this.locationName = locationTimeAdjustment.getLocationName();
            this.hoursAlignment = locationTimeAdjustment.getHoursAlignment();
            this.offsetFromMainCity = locationTimeAdjustment.getOffsetFromMainCity();
        }

        public LocationTimeAdjustment build() {
            return new LocationTimeAdjustment(this);
        }

        public Builder locationName(String locationName) {
            this.locationName = locationName;
            return this;
        }

        public Builder hoursAlignment(String[] hoursAlignment) {
            this.hoursAlignment = hoursAlignment;
            return this;
        }

        public Builder offsetFromMainCity(String offsetFromMainCity) {
            this.offsetFromMainCity = offsetFromMainCity;
            return this;
        }


    }
}
