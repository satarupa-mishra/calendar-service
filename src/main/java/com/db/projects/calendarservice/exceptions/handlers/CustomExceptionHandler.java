package com.db.projects.calendarservice.exceptions.handlers;

import com.db.projects.calendarservice.constants.CalendarConstant;
import com.db.projects.calendarservice.exceptions.DuplicateRecordException;
import com.db.projects.calendarservice.exceptions.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom Exception Handler Class
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * In case of RecordNotFoundException, this aspect will be used graceful message to user
     * message= customized message the Exception sent as below with http bad request
     * errorCode = ERR_RESOURCE_NOT_FOUND
     * errorMessage = message passed from RecordNotFoundException
     *
     * @param e
     * @param w
     * @return
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e, WebRequest w){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(CalendarConstant.ERROR_CODE, CalendarConstant.ERROR_CODE_VALUE_NO_RECORD);
        responseBody.put(CalendarConstant.ERROR_MESSAGE, e.getLocalizedMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * In case of DuplicateRecordException, this aspect will be used graceful message to user
     * message= customized message the Exception sent as below with http bad request
     * errorCode = ERR_DUPLICATE_RESOURCE
     * errorMessage = message passed from DuplicateRecordException
     * @param e
     * @param w
     * @return
     */
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(DuplicateRecordException e, WebRequest w){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put(CalendarConstant.ERROR_CODE, CalendarConstant.ERROR_CODE_VALUE_DUPLICATE_RECORD);
        responseBody.put(CalendarConstant.ERROR_MESSAGE, e.getLocalizedMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    /**
     * in case of Validation failed Exception, this aspect will be used graceful message to user as below
     * errorCode = ERR_RESOURCE_NOT_FOUND
     * errorMessage = ERR_INVALID_INPUT
     * errorFields = {field}: {error message as set in validation}
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        Map<Object,Object> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors().stream().forEach(x->{
            error.put(x.getField(),x.getDefaultMessage());
        });
        responseBody.put(CalendarConstant.ERROR_CODE, CalendarConstant.ERROR_CODE_VALUE_VALIDATION);
        responseBody.put(CalendarConstant.ERROR_MESSAGE, CalendarConstant.ERROR_MESSAGE_VALUE_VALIDATION);
        responseBody.put(CalendarConstant.ERROR_FIELD, error);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}

