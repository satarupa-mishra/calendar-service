package com.db.projects.calendarservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

/**
 * DTO class for Notification details for the Calendar Event
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Notification {

    @Range(min=0, max=59, message="Enter minutes from 0 to 59")
    private int minute;

    @Range(min=0, max=23, message="Enter hours from 0 to 23")
    private int hour;

    @Range(min=0, max=6, message="Enter days from 0 to 6")
    private int day;

    private int week;
}
