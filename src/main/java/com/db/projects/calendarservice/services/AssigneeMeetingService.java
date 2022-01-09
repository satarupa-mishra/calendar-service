package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.entities.CalendarEventEntity;

import java.util.Set;

/**
 * service class to handle business logic w.r.t. assigneeMeeting and call database repository to
 * perform database operations
 */
public interface AssigneeMeetingService {

    Set<AssigneeMeeting> saveAssignee(CalendarEventEntity savedCalendarEventEntity, Set<AssigneeMeeting> assigneeMeetings);

    Set<AssigneeMeeting> findAssigneeByCalendarEventEntity(CalendarEventEntity calendarEventEntity);

    AssigneeMeeting updateAssigneeMeeting(CalendarEventEntity calendarEventEntity, String assigneeEmail, AssigneeMeeting assigneeMeeting);

    Set<AssigneeMeeting> addAssigneeMeetingsByEventId(CalendarEventEntity calendarEventEntity, AssigneeMeeting assigneeMeetings);
}
