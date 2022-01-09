package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.dto.CalendarEventDTO;

import java.util.List;
import java.util.Set;

/**
 * service class to handle business logic w.r.t. calendarEvent and call database repository to
 * perform database operations
 */
public interface CalendarService {
    CalendarEventDTO createEvent(CalendarEventDTO calendarEventDTO);
    CalendarEventDTO updateEvent(int eventId, CalendarEventDTO calendarEventDTO);
    void deleteEvent(int eventId);
    Set<AssigneeMeeting> addAssigneeMeetings(AssigneeMeeting assigneeMeeting, int eventId);
    AssigneeMeeting updateAssigneeMeeting(int eventId, String assigneeEmail, AssigneeMeeting assigneeMeeting);
    CalendarEventDTO findEventById(int eventId);
    List<CalendarEventDTO> findEventsByOrganiserEmail(String organiserEmail);
}
