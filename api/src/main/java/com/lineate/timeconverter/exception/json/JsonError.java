package com.lineate.timeconverter.exception.json;


import com.lineate.timeconverter.exception.ErrorCode;

import java.util.Objects;
/*Class needs for providing info about error*/
public class JsonError {
    private ErrorCode errorCode;
    private String message;

    public JsonError() {
    }

    public JsonError(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public JsonError(ErrorCode code) {
        errorCode = code;
    }

    public JsonError(String message) {
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof JsonError)) { return false; }
        JsonError jsonError = (JsonError) o;
        return getErrorCode() == jsonError.getErrorCode() &&
                Objects.equals(getMessage(), jsonError.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getErrorCode(), getMessage());
    }

    @Override
    public String toString() {
        return "JsonError{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
