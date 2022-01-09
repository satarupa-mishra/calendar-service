package com.db.projects.calendarservice.exceptions;

/**
 * Custom Exception class when No record is found in database
 */
public class RecordNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public RecordNotFoundException(String message){
        super(message);
    }

}
