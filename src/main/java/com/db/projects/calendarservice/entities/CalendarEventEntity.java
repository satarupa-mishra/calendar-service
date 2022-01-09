package com.db.projects.calendarservice.entities;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

/**
 * Entity class for calendar event
 * Table name = calendar_event
 * It has one to many relationship with assignee_meeting table and foreign key is in the later table
 * It has One to One relationship with notification table and foreign key is in the later table
 */
@Entity
@Table(name="calendar_event")
public class CalendarEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eventType;
    private String organiserEmail;

    private Calendar startTime;
    private Calendar endTime;
    private String location;
    private String agenda;
    private String status;

    @OneToOne(mappedBy = "calendarEventEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private NotificationEntity notificationEntity;

    @OneToMany(mappedBy = "calendarEventEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AssigneeMeetingEntity> assigneeMeetingEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOrganiserEmail() {
        return organiserEmail;
    }

    public void setOrganiserEmail(String organiserEmail) {
        this.organiserEmail = organiserEmail;
    }


    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    @Override
    public String toString() {
        return "CalendarEventEntity{" +
                "id=" + id +
                ", eventType='" + eventType + '\'' +
                ", organiserEmail='" + organiserEmail + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", location='" + location + '\'' +
                ", agenda='" + agenda + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
