package com.db.projects.calendarservice.dto;

import com.db.projects.calendarservice.validations.CustomEventValidator;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Calendar;
import java.util.Set;

/**
 * DTO class for calendar event
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarEventDTO {

    private int id;

    @NotEmpty(message="Field is required.")
    @CustomEventValidator
    private String eventType;

    @NotEmpty(message="Field is required.")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String organiserEmail;

    @NotNull(message="Field is required.")
    private Calendar startTime;

    private Calendar endTime;

    private String location;

    @NotEmpty(message="Field is required.")
    private String agenda;

    private String status;

    @Valid
    private Notification notification;

    @Valid
    private Set<AssigneeMeeting> assigneeMeetings;


}
