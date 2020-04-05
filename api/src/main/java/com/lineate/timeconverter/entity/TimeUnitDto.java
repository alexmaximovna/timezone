package com.lineate.timeconverter.entity;

import javax.validation.constraints.Pattern;

public class TimeUnitDto {
    @Pattern(regexp = "[0-9]*", message = "Not correct number")
    private String number;
    private String typeUnit;

    public TimeUnitDto() {
    }

    public TimeUnitDto(String number, String typeUnit) {
        this.number = number;
        this.typeUnit = typeUnit;
    }
    /**
     * Method return number
     */
    public String getNumber() {
        return number;
    }
    /**
     * Method change number
     *
     * @param number - new value for number
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * Method return typeUnit
     */
    public String getTypeUnit() {
        return typeUnit;
    }
    /**
     * Method change typeUnit
     *
     * @param typeUnit - new value for typeUnit
     */
    public void setTypeUnit(String typeUnit) {
        this.typeUnit = typeUnit;
    }
}
