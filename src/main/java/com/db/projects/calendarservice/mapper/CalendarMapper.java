package com.db.projects.calendarservice.mapper;

import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.dto.CalendarEventDTO;
import com.db.projects.calendarservice.dto.Notification;
import com.db.projects.calendarservice.entities.AssigneeMeetingEntity;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.entities.NotificationEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * mapper class to convert from DTO to Entity and vice versa
 */
public class CalendarMapper {

    /**
     * map CalendarEventDTO to CalendarEventEntity
     * @param calendarEventDTO
     * @return
     */
    public static CalendarEventEntity mapCalendarDtoToEntity(CalendarEventDTO calendarEventDTO) {
        CalendarEventEntity calendarEventEntity = new CalendarEventEntity();
        calendarEventEntity.setId(calendarEventDTO.getId());
        calendarEventEntity.setEventType(calendarEventDTO.getEventType());
        calendarEventEntity.setOrganiserEmail(calendarEventDTO.getOrganiserEmail());
        calendarEventEntity.setStartTime(calendarEventDTO.getStartTime());
        calendarEventEntity.setEndTime(calendarEventDTO.getEndTime());
        calendarEventEntity.setLocation(calendarEventDTO.getLocation());
        calendarEventEntity.setAgenda(calendarEventDTO.getAgenda());
        calendarEventEntity.setStatus(calendarEventDTO.getStatus());
        return calendarEventEntity;
    }


    /**
     * map CalendarEventEntity to CalendarEventDTO
     * @param calendarEventEntity
     * @return
     */
    public static CalendarEventDTO mapCalendarEntityToDto(CalendarEventEntity calendarEventEntity) {
        CalendarEventDTO calendarEventDTO = new CalendarEventDTO();
        calendarEventDTO.setId(calendarEventEntity.getId());
        calendarEventDTO.setEventType(calendarEventEntity.getEventType());
        calendarEventDTO.setOrganiserEmail(calendarEventEntity.getOrganiserEmail());
        calendarEventDTO.setStartTime(calendarEventEntity.getStartTime());
        calendarEventDTO.setEndTime(calendarEventEntity.getEndTime());
        calendarEventDTO.setLocation(calendarEventEntity.getLocation());
        calendarEventDTO.setAgenda(calendarEventEntity.getAgenda());
        calendarEventDTO.setStatus(calendarEventEntity.getStatus());
        return calendarEventDTO;
    }

    /**
     * map Notification to NotificationEntity
     * @param notification
     * @return
     */
    public static NotificationEntity mapNotificationDtoToEntity(Notification notification) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMinute(notification.getMinute());
        notificationEntity.setHour(notification.getHour());
        notificationEntity.setDay(notification.getDay());
        notificationEntity.setWeek(notification.getWeek());

        return notificationEntity;
    }

    /**
     * map NotificationEntity to Notification
     * @param notificationEntity
     * @return
     */
    public static Notification mapNotificationEntityToDto(NotificationEntity notificationEntity) {
        Notification notification = new Notification();
        notification.setMinute(notificationEntity.getMinute());
        notification.setHour(notificationEntity.getHour());
        notification.setDay(notificationEntity.getDay());
        notification.setWeek(notificationEntity.getWeek());

        return notification;
    }

    /**
     * map Set<AssigneeMeeting> to Set<AssigneeMeetingEntity>
     * set CalendarEventEntity to each AssigneeMeeting
     * @param savedCalenderEventEntity
     * @param assigneeMeetings
     * @return
     */
    public static Set<AssigneeMeetingEntity> mapAssigneeMeetingSetDtoToEntity(CalendarEventEntity savedCalenderEventEntity,
                                                                           Set<AssigneeMeeting> assigneeMeetings) {
        if (assigneeMeetings != null && assigneeMeetings.size() > 0) {
            Set<AssigneeMeetingEntity> assigneeMeetingEntities = new HashSet<>();
            assigneeMeetings.stream().forEach(assigneeMeeting -> {
                AssigneeMeetingEntity assigneeMeetingEntity = mapAssigneeMeetingDtoToEntity(assigneeMeeting);
                assigneeMeetingEntity.setCalendarEventEntity(savedCalenderEventEntity);
                assigneeMeetingEntities.add(assigneeMeetingEntity);
            });
            return assigneeMeetingEntities;
        } else {
            return null;
        }
    }

    /**
     * map AssigneeMeeting to AssigneeMeetingDTO
     * @param assigneeMeeting
     * @return
     */
    public static AssigneeMeetingEntity mapAssigneeMeetingDtoToEntity(AssigneeMeeting assigneeMeeting) {
        AssigneeMeetingEntity assigneeMeetingEntity = new AssigneeMeetingEntity();
        assigneeMeetingEntity.setAssigneeEmail(assigneeMeeting.getAssigneeEmail());
        assigneeMeetingEntity.setInviteResponse(assigneeMeeting.getInviteResponse());
        assigneeMeetingEntity.setInviteResponseStatus(assigneeMeeting.getInviteResponseStatus());
        return assigneeMeetingEntity;

    }

    /**
     * map Set<AssigneeMeetingEntity> to Set<AssigneeMeeting>
     * @param assigneeMeetingEntities
     * @return
     */
    public static Set<AssigneeMeeting> mapAssigneeMeetingSetEntityToDto(Set<AssigneeMeetingEntity> assigneeMeetingEntities) {
        if (assigneeMeetingEntities != null && assigneeMeetingEntities.size() > 0) {
            Set<AssigneeMeeting> assigneeMeetings = new HashSet<>();
            assigneeMeetingEntities.stream().forEach(x -> {
                AssigneeMeeting assigneeMeeting = mapAssigneeMeetingEntityToDto(x);
                assigneeMeetings.add(assigneeMeeting);
            });
            return assigneeMeetings;
        } else {
            return null;
        }
    }

    /**
     * map AssigneeMeetingEntity to AssigneeMeeting
     * @param assigneeMeetingEntity
     * @return
     */
    public static AssigneeMeeting mapAssigneeMeetingEntityToDto(AssigneeMeetingEntity assigneeMeetingEntity) {
        AssigneeMeeting assigneeMeeting = new AssigneeMeeting();
        assigneeMeeting.setAssigneeEmail(assigneeMeetingEntity.getAssigneeEmail());
        assigneeMeeting.setInviteResponse(assigneeMeetingEntity.getInviteResponse());
        assigneeMeeting.setInviteResponseStatus(assigneeMeetingEntity.getInviteResponseStatus());
        return assigneeMeeting;
    }
}
