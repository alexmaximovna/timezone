package com.lineate.timeconverter.exception;

/* Enum of probably errors*/
public enum ErrorCode {
    OVERRIDE_LIMIT("The count of cities should be no more than 10"),
    WRONG_CITY("Not correct name of city"),
    CANT_DELETE_CITY("Can`t remove city, list is empty"),
    EXIST_CITY("City already exists in list"),
    NOT_EXIST_CITY("City doesnt  exists in list"),
    WRONG_ID_LIST("Wrong id for list"),
    WRONG_NUMBER("Not correct numder"),
    NOT_EXIST_LIST("List doesn't exist ");

    private String errorCode;


    ErrorCode(String errorCode) {
        this.errorCode = errorCode;

    }
    /*Method return type of error by its string description*/
    public static ErrorCode getCode(String errorCode) {
        for (ErrorCode code : ErrorCode.values()) {
            if (code.errorCode.equalsIgnoreCase(errorCode)) {
                return code;
            }
        }
        return null;

    }
    /*Method return string description of error*/
    public String getErrorCode() {
        return errorCode;
    }

}
