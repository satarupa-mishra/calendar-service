package com.db.projects.calendarservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Entity class for notification
 * Table name = notification
 * It has one to one relationship with calendar_event table
 * and has join column =notification_event_id with value of the primary key of calendar_event table
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int minute;
    private int hour;
    private int day;
    private int week;

    @OneToOne
    @JoinColumn(name="notification_event_id")
    private CalendarEventEntity calendarEventEntity;

}
