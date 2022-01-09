package com.db.projects.calendarservice.repositories;

import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for making database operation with notification table
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Integer> {

    NotificationEntity findByCalendarEventEntity(CalendarEventEntity calendarEventEntity);

}
