package com.db.projects.calendarservice.controller;

import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.dto.CalendarEventDTO;
import com.db.projects.calendarservice.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Controller class for Calendar Service
 */
@RestController
@RequestMapping("/calenders/v1/")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    /**
     * End point#1
     * This end point is responsible for post call to save calendar event and return saved Calendar Event
     * if input Calendar event is validated , then it will save and return status code=200
     * else, graceful error message for failed validation with error fields and messages will be returned
     * HTTP POST:/calenders/v1/events
     * @param calendarEventDTO
     * @return
     */
    @PostMapping("/events")
    public ResponseEntity<CalendarEventDTO> createEvent(@Valid @RequestBody CalendarEventDTO calendarEventDTO) {
        CalendarEventDTO response = calendarService.createEvent(calendarEventDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * end point#2
     * This end point is responsible for Get call. It will return  calendar event for the input eventId
     * HTTP GET:/calenders/v1/events/ids/{id}
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * @param eventId
     * @return
     */
    @GetMapping("/events/ids/{eventId}")
    public ResponseEntity<CalendarEventDTO> findEventById(@PathVariable("eventId") int eventId) {
        CalendarEventDTO response = calendarService.findEventById(eventId);

        return ResponseEntity.ok(response);
    }

    /**
     * end point#3
     * This end point is responsible for Get call. It will return  calendar event List for the input organiserEmail
     * HTTP GET:/calenders/v1/events/emails/{organiserEmail}
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * @param organiserEmail
     * @return
     */
    @GetMapping("/events/emails/{organiserEmail}")
    public ResponseEntity<List<CalendarEventDTO>> findEventsByOrganiserEmail(@PathVariable("organiserEmail") String organiserEmail) {
        List<CalendarEventDTO> response = calendarService.findEventsByOrganiserEmail(organiserEmail);

        return ResponseEntity.ok(response);
    }

    /**
     * End point#4
     * HTTP DELETE:/calenders/v1/events/{eventId}
     * Delete call to delete the calendar event against the input evenId path variable
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * @param eventId
     * @return
     */
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity deleteEvent(@PathVariable("eventId") int eventId) {
        calendarService.deleteEvent(eventId);
        return ResponseEntity.ok(null);
    }

    /**
     * End point#5
     * HTTP PUT:/calenders/v1/events/{eventId}
     * This end point is responsible for updating the calendar event against input eventId
     * update in either or all of the below attributes
     * start time, end time, location, notification, status
     * if input Calendar event is validated , then it will update the calendar event and
     * return updated calendar event and status code 200
     * else graceful error message for failed validation with error fields and messages will be returned
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * @param calendarEventDTO
     * @param eventId
     * @return
     */
    @PutMapping("/events/{eventId}")
    public ResponseEntity<CalendarEventDTO> updateEvent(@Valid @RequestBody CalendarEventDTO calendarEventDTO,
                                                        @PathVariable("eventId") int eventId) {

        CalendarEventDTO response = calendarService.updateEvent(eventId, calendarEventDTO);

        return ResponseEntity.ok(response);
    }

    /**
     * End point#6
     * HTTP PUT:/calenders/v1/events/{eventId}/assigneeMeetings/{assigneeEmail}
     * This end point is responsible for updating the assignee details of calendar event
     * against input eventId and assigneeEmail
     * if input Calendar event is validated , then it will update the assigneeMeeting Details and
     * return updated calendar event and status code 200
     * else graceful error message for failed validation with error fields and messages will be returned
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * @param assigneeMeeting
     * @param eventId
     * @return
     */
    @PutMapping("/events/{eventId}/assigneeMeetings/{assigneeEmail}")
    public ResponseEntity<AssigneeMeeting> updateAssigneeMeeting(@Valid @RequestBody AssigneeMeeting assigneeMeeting,
                                                                 @PathVariable("eventId") int eventId,
                                                                 @PathVariable("assigneeEmail") String assigneeEmail){
        AssigneeMeeting response = calendarService.updateAssigneeMeeting(eventId,assigneeEmail,assigneeMeeting );
        return ResponseEntity.ok(response);
    }

    /**
     * End point#7
     * HTTP PUT:/calenders/v1/events/{eventId}/assigneeMeetings
     * This end point is responsible for adding the assignee details of calendar event against input eventId
     * if input Calendar event is validated , then it will add the assigneeMeeting Details and
     * return updated calendar event and status code 200
     * else graceful error message for failed validation with error fields and messages will be returned
     * if no record is found in database against the input request param, then graceful error message with resource not found will be shown
     * if input assigneeEmail is already present in database for the input eventId and assigneeMeetings,
     * then Duplicate Record Exception will be shown gracefully
     * @param assigneeMeeting
     * @param eventId
     * @return
     */
    @PutMapping("/events/{eventId}/assigneeMeetings")
    public ResponseEntity<Set<AssigneeMeeting>> addAssigneeMeeting(@Valid @RequestBody AssigneeMeeting assigneeMeeting,
                                                                  @PathVariable("eventId") int eventId){
        Set<AssigneeMeeting>  response = calendarService.addAssigneeMeetings(assigneeMeeting, eventId);
        return ResponseEntity.ok(response);
    }





}
