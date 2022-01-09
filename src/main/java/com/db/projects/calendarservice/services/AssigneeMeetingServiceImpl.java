package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.constants.CalendarConstant;
import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.dto.CalendarEventDTO;
import com.db.projects.calendarservice.entities.AssigneeMeetingEntity;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.exceptions.DuplicateRecordException;
import com.db.projects.calendarservice.exceptions.RecordNotFoundException;
import com.db.projects.calendarservice.mapper.CalendarMapper;
import com.db.projects.calendarservice.repositories.AssigneeMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssigneeMeetingServiceImpl implements AssigneeMeetingService {

    @Autowired
    AssigneeMeetingRepository assigneeMeetingRepository;

    /**
     * method to save input assigneeMeetings for input calendarEventEntity
     *
     * @param savedCalendarEventEntity
     * @param assigneeMeetings
     * @return
     */
    @Override
    public Set<AssigneeMeeting> saveAssignee(CalendarEventEntity savedCalendarEventEntity, Set<AssigneeMeeting> assigneeMeetings) {
        if (assigneeMeetings != null && assigneeMeetings.size() > 0) {
            Set<AssigneeMeetingEntity> assigneeMeetingEntities =
                    CalendarMapper.mapAssigneeMeetingSetDtoToEntity(savedCalendarEventEntity, assigneeMeetings);
            Set<AssigneeMeetingEntity> savedAssigneeMeetingEntities = assigneeMeetingRepository
                    .saveAll(assigneeMeetingEntities).stream().collect(Collectors.toSet());
            return CalendarMapper.mapAssigneeMeetingSetEntityToDto(savedAssigneeMeetingEntities);
        } else {
            return null;
        }

    }

    /**
     * method to fetch assigneeMeetingEntities for input calendarEventEntity
     *
     * @param calendarEventEntity
     * @return
     */
    public Set<AssigneeMeetingEntity> findAssigneeEntitiesByCalendarEventEntity(CalendarEventEntity calendarEventEntity) {
        Set<AssigneeMeetingEntity> assigneeMeetingEntities = assigneeMeetingRepository.
                findAllByCalendarEventEntity(calendarEventEntity).stream().collect(Collectors.toSet());
        return assigneeMeetingEntities;
    }

    /**
     * method to fetch assigneeMeetings DTOs for input calendarEventEntity
     *
     * @param calendarEventEntity
     * @return
     */
    @Override
    public Set<AssigneeMeeting> findAssigneeByCalendarEventEntity(CalendarEventEntity calendarEventEntity) {
        Set<AssigneeMeetingEntity> assigneeMeetingEntities =
                findAssigneeEntitiesByCalendarEventEntity(calendarEventEntity);
        return CalendarMapper.mapAssigneeMeetingSetEntityToDto(assigneeMeetingEntities);
    }

    /**
     * method to update the assigneeEventEntity for input calendarEventEntity, assigneeEmail with input assigneeMeeting details.
     * return updated AssigneeMeeting
     *
     * @param calendarEventEntity
     * @param assigneeEmail
     * @return
     */
    @Override
    public AssigneeMeeting updateAssigneeMeeting(CalendarEventEntity calendarEventEntity,
                                                 String assigneeEmail, AssigneeMeeting assigneeMeeting) {
        //get assigneeMeetingEntity for input calendarEventEntity and assigneeEmail
        AssigneeMeetingEntity assigneeMeetingEntity =
                assigneeMeetingRepository.findByCalendarEventEntityAndAssigneeEmail(calendarEventEntity, assigneeEmail);
        if (assigneeMeetingEntity == null) {
            throw new RecordNotFoundException(CalendarConstant.ERROR_MESSAGE_VALUE_NO_RECORD);
        } else {
            //set the assigneeMeetingEntity with input assigneeMeeting details
            if (assigneeMeeting.getInviteResponse() != null) {
                assigneeMeetingEntity.setInviteResponse(assigneeMeeting.getInviteResponse());
            }
            if (assigneeMeeting.getInviteResponseStatus() != null) {
                assigneeMeetingEntity.setInviteResponseStatus(assigneeMeeting.getInviteResponseStatus());
            }
            //save assigneeMeetingEntity and then return corresponding assigneeMeeting
            AssigneeMeetingEntity savedAssigneeMeetingEntity
                    = assigneeMeetingRepository.save(assigneeMeetingEntity);
            return CalendarMapper.mapAssigneeMeetingEntityToDto(savedAssigneeMeetingEntity);
        }
    }

    /**
     * method to add assigneeMeeting to existing assigneeMeetingEntities for input calendarEventEntity
     * if input assigneeMeeting assigneeEmail is already present for input calendarEventEntity,
     * then throw Duplicate Record Exception gracefully
     * @param calendarEventEntity
     * @param assigneeMeeting
     * @return
     */
    @Override
    public Set<AssigneeMeeting> addAssigneeMeetingsByEventId(CalendarEventEntity calendarEventEntity, AssigneeMeeting assigneeMeeting) {
        //get assigneeMeetingEntity list for input calendarEventEntity
        List<AssigneeMeetingEntity> assigneeMeetingEntities =
                assigneeMeetingRepository.findAllByCalendarEventEntity(calendarEventEntity);
        if (assigneeMeetingEntities == null) {
            throw new RecordNotFoundException(CalendarConstant.ERROR_MESSAGE_VALUE_NO_RECORD);
        } else {
            assigneeMeetingEntities.stream().forEach(assigneeMeetingEntity ->
            {
                if (assigneeMeetingEntity.getAssigneeEmail().equals(assigneeMeeting.getAssigneeEmail())) {
                    throw new DuplicateRecordException(CalendarConstant.ERROR_MESSAGE_VALUE_DUPLICATE_ASSIGNEE_MEETING_RECORD);
                }
            });

            AssigneeMeetingEntity assigneeMeetingEntity = CalendarMapper.mapAssigneeMeetingDtoToEntity(assigneeMeeting);
            assigneeMeetingEntity.setCalendarEventEntity(calendarEventEntity);
            assigneeMeetingRepository.save(assigneeMeetingEntity);

        }
        //get updated assigneeMeetingEntity List for input calendarEventEntity
        List<AssigneeMeetingEntity> savedAssigneeMeetingEntities =
                assigneeMeetingRepository.findAllByCalendarEventEntity(calendarEventEntity);
        Set<AssigneeMeeting> retAssigneeMeetings = CalendarMapper
                .mapAssigneeMeetingSetEntityToDto(savedAssigneeMeetingEntities.stream().collect(Collectors.toSet()));
        return retAssigneeMeetings;
    }
}
