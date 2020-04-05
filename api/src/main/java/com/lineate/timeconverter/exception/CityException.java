package com.lineate.timeconverter.exception;

/* Class contains info exception with cities */
public class CityException extends RuntimeException {
    private  ErrorCode errorCode;


    public CityException(ErrorCode errorCode, Exception ex) {
        super(ex);
        this.errorCode = errorCode;
    }

    public CityException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public CityException() {
        super();
    }

    public CityException(String message) {
        super(message);
    }

    public CityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityException(Throwable cause) {
        super(cause);
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
