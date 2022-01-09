package com.db.projects.calendarservice.constants;

import java.util.ArrayList;
import java.util.List;

public class CalendarConstant {public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_CODE_VALUE_VALIDATION = "ERR_INVALID_INPUT";
    public static final String ERROR_CODE_VALUE_NO_RECORD = "ERR_RESOURCE_NOT_FOUND";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String ERROR_MESSAGE_VALUE_VALIDATION = "Invalid input. Parameter name: ";
    public static final String ERROR_MESSAGE_VALUE_NO_RECORD = "Requested resource is not available";
    public static final String ERROR_FIELD = "errorFields";
    public static final String EVENT_TYPE_MEETING = "Meeting";
    public static final String EVENT_TYPE_TASK = "Task";
    public static final List<String> VALID_EVENT_TYPE_LIST = new ArrayList<>(){
        {
            add(EVENT_TYPE_MEETING);
            add(EVENT_TYPE_TASK);
        }
    };
    public static final String ERROR_MESSAGE_VALUE_DUPLICATE_ASSIGNEE_MEETING_RECORD = "Requested assignee is already available";
    public static final String ERROR_CODE_VALUE_DUPLICATE_RECORD = "ERR_DUPLICATE_RESOURCE";
}
