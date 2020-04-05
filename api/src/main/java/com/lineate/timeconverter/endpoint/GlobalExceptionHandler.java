package com.lineate.timeconverter.endpoint;

import com.lineate.timeconverter.exception.CityException;
import com.lineate.timeconverter.exception.ErrorCode;
import com.lineate.timeconverter.exception.json.JsonError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Methos handles CityException
     * @param ex type of Exception
     * @return json with message about error
     */
    @ExceptionHandler(CityException.class)
    public ResponseEntity<?> handleProblemNotFound(CityException ex) {
        ErrorCode code = ex.getErrorCode();
        return new ResponseEntity<>(new JsonError(code, code.getErrorCode()), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        List<JsonError> list = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldError error = fieldError;
            String nameField = fieldError.getField();
            list.add(new JsonError(ErrorCode.WRONG_NUMBER, error.getDefaultMessage()));
        }
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }
}
