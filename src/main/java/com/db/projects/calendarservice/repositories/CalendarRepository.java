package com.db.projects.calendarservice.repositories;

import com.db.projects.calendarservice.entities.CalendarEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for making database operation with calendar_event table
 */
@Repository
public interface CalendarRepository extends JpaRepository<CalendarEventEntity,Integer> {

    List<CalendarEventEntity> findByOrganiserEmail(String organiserEmail);

}
