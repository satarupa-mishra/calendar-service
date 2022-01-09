package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.dto.Notification;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.entities.NotificationEntity;
import com.db.projects.calendarservice.mapper.CalendarMapper;
import com.db.projects.calendarservice.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    /**
     * method to fetch notification DTO against input calendarEventEntity
     * @param calendarEventEntity
     * @return
     */
    @Override
    public Notification findNotificationByCalendarEventEntity(CalendarEventEntity calendarEventEntity) {

        NotificationEntity notificationEntity =
                findNotificationEntityByCalendarEventEntity(calendarEventEntity);
        Notification notification = CalendarMapper.mapNotificationEntityToDto(notificationEntity);
        return notification;
    }

    @Override
    public NotificationEntity findNotificationEntityByCalendarEventEntity(CalendarEventEntity calendarEventEntity) {
        NotificationEntity notificationEntity =
                notificationRepository.findByCalendarEventEntity(calendarEventEntity);
        return notificationEntity;
    }

    /**
     * method to save notification in entity table with input calendarEventEntity for input notification
     * @param savedCalendarEventEntity
     * @param notification
     * @return
     */
    @Override
    public NotificationEntity saveNotification(CalendarEventEntity savedCalendarEventEntity, Notification notification) {
        NotificationEntity notificationEntity = CalendarMapper.mapNotificationDtoToEntity(notification);
        notificationEntity.setCalendarEventEntity(savedCalendarEventEntity);
        NotificationEntity savedNotificationEntity = saveNotification(notificationEntity);
        return savedNotificationEntity;
    }

    /**
     * method to save notification in entity table for input notification
     * @param notificationEntity
     * @return
     */
    @Override
    public NotificationEntity saveNotification(NotificationEntity notificationEntity) {
        NotificationEntity savedNotificationEntity = notificationRepository.save(notificationEntity);
        return savedNotificationEntity;
    }




}
