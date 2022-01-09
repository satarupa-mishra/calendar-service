package com.db.projects.calendarservice.repositories;

import com.db.projects.calendarservice.entities.AssigneeMeetingEntity;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for making database operation with assignee_meeting table
 */
@Repository
public interface AssigneeMeetingRepository extends JpaRepository<AssigneeMeetingEntity, Integer> {

    List<AssigneeMeetingEntity> findAllByCalendarEventEntity(CalendarEventEntity calendarEventEntity);
    AssigneeMeetingEntity findByCalendarEventEntityAndAssigneeEmail(CalendarEventEntity calendarEventEntity, String assigneeEmail);

}
