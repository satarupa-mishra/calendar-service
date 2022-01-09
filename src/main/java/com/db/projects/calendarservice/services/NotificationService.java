package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.dto.Notification;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.entities.NotificationEntity;

/**
 * service class to handle business logic w.r.t. notification and call database repository to
 * perform database operations
 */
public interface NotificationService {
    NotificationEntity findNotificationEntityByCalendarEventEntity(CalendarEventEntity calendarEventEntity);
    Notification findNotificationByCalendarEventEntity(CalendarEventEntity calendarEventEntity);

    NotificationEntity saveNotification(CalendarEventEntity savedCalendarEventEntity, Notification notification);
    NotificationEntity saveNotification(NotificationEntity notificationEntity);

}
