package com.db.projects.calendarservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Entity class for assignee meeting
 * Table name = assignee_meeting
 * It has Many to one relationship with calendar_event table
 * and has join column =assignee_event_id with value of the primary key of calendar_event table
 */
@Entity
@Data
@ToString
@Table(name="assignee_meeting")
public class AssigneeMeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String assigneeEmail;
    private String inviteResponseStatus;
    private String inviteResponse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="assignee_event_id", nullable = false)
    private CalendarEventEntity calendarEventEntity;

}
