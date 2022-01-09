package com.db.projects.calendarservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * DTO for Assignee details of the Calendar Event
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssigneeMeeting {

    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String assigneeEmail;

    private String inviteResponseStatus;
    private String inviteResponse;

}

