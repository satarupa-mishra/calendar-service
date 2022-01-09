package com.db.projects.calendarservice.services;

import com.db.projects.calendarservice.constants.CalendarConstant;
import com.db.projects.calendarservice.dto.AssigneeMeeting;
import com.db.projects.calendarservice.dto.CalendarEventDTO;
import com.db.projects.calendarservice.dto.Notification;
import com.db.projects.calendarservice.entities.CalendarEventEntity;
import com.db.projects.calendarservice.entities.NotificationEntity;
import com.db.projects.calendarservice.exceptions.RecordNotFoundException;
import com.db.projects.calendarservice.mapper.CalendarMapper;
import com.db.projects.calendarservice.repositories.CalendarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CalendarServiceImpl implements CalendarService{

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AssigneeMeetingService assigneeMeetingService;

    /**
     * method to save input calendar event with notification and/or assigneeMeeting details
     * in corresponding databases
     * @param calendarEventDTO
     * @return
     */
    @Override
    public CalendarEventDTO createEvent(CalendarEventDTO calendarEventDTO) {

        //save input calendar event
        CalendarEventEntity calendarEventEntity = CalendarMapper.mapCalendarDtoToEntity(calendarEventDTO);
        CalendarEventEntity savedCalendarEventEntity =calendarRepository.save(calendarEventEntity);

        CalendarEventDTO retCalendarEventDTO = CalendarMapper.mapCalendarEntityToDto(savedCalendarEventEntity);

        //save notification for the input calendar event
        NotificationEntity notificationEntity =
                notificationService.saveNotification(savedCalendarEventEntity, calendarEventDTO.getNotification());
        retCalendarEventDTO.setNotification(CalendarMapper.mapNotificationEntityToDto(notificationEntity));

        //save assigneeMeetings for the input calendar event
        Set<AssigneeMeeting> assigneeMeetings =
                assigneeMeetingService.saveAssignee(savedCalendarEventEntity, calendarEventDTO.getAssigneeMeetings());
        retCalendarEventDTO.setAssigneeMeetings(assigneeMeetings);

        return retCalendarEventDTO;

    }

    /**
     * method to update Calendar Event for CalendarEventEntity and Notification Entity
     * with the input eventId and to be updated input calendar event
     * then return updated Calendar event DTO
     * @param eventId
     * @param calendarEventDTO
     * @return
     */
    @Override
    public CalendarEventDTO updateEvent(int eventId, CalendarEventDTO calendarEventDTO) {

        //fetch database calendarEvent for input eventId and then update it with input calendar event
        CalendarEventEntity calendarEventEntity = findCalendarEventEntityById(eventId);
        calendarEventEntity = updateCalendarEvent(calendarEventDTO, calendarEventEntity);
        //save the updated calendarEventEntity to database
        CalendarEventEntity updatedCalendarEntity =
                calendarRepository.save(calendarEventEntity);
        CalendarEventDTO retCalendarEventDTO = CalendarMapper.mapCalendarEntityToDto(updatedCalendarEntity);

        if(calendarEventDTO.getNotification()!=null){
            Notification notification = updateNotificationEntity(calendarEventDTO,updatedCalendarEntity);
            retCalendarEventDTO.setNotification(notification);
        }
        retCalendarEventDTO.setAssigneeMeetings(assigneeMeetingService.findAssigneeByCalendarEventEntity(updatedCalendarEntity));
        return retCalendarEventDTO;
    }

    /**
     * update notification entity with input updatedCalendarEntity and with calendarEventDTO's notification
     * @param calendarEventDTO
     * @param updatedCalendarEntity
     * @return
     */
    private Notification updateNotificationEntity(CalendarEventDTO calendarEventDTO, CalendarEventEntity updatedCalendarEntity) {
        //get notification DTO for input updatedCalendarEntity
        NotificationEntity notificationEntity =
                notificationService.findNotificationEntityByCalendarEventEntity(updatedCalendarEntity);
        //set notification with input calendarEventDTO notification details
        notificationEntity.setDay(calendarEventDTO.getNotification().getDay());
        notificationEntity.setWeek(calendarEventDTO.getNotification().getWeek());
        notificationEntity.setHour(calendarEventDTO.getNotification().getHour());
        notificationEntity.setMinute(calendarEventDTO.getNotification().getMinute());

        //update notification entity with saved notification
        NotificationEntity updatedNotificationEntity =
                notificationService.saveNotification(notificationEntity);
        Notification retNotification = CalendarMapper.mapNotificationEntityToDto(updatedNotificationEntity);
        return retNotification;

    }

    /**
     * update input calendarEventEntity with input calendarEventDTO
     * either or all of startTime, endTime, status, location can only be updated
     * @param calendarEventDTO
     * @param calendarEventEntity
     */
    private CalendarEventEntity  updateCalendarEvent(CalendarEventDTO calendarEventDTO,
                                                    CalendarEventEntity calendarEventEntity) {
        if(calendarEventDTO.getStartTime()!=null){
            calendarEventEntity.setStartTime(calendarEventDTO.getStartTime());
        }
        if(calendarEventDTO.getEndTime()!=null){
            calendarEventEntity.setEndTime(calendarEventDTO.getEndTime());
        }
        if(calendarEventDTO.getStatus()!=null){
            calendarEventEntity.setStatus(calendarEventDTO.getStatus());
        }
        if(calendarEventDTO.getLocation()!=null){
            calendarEventEntity.setLocation(calendarEventDTO.getLocation());
        }
        return calendarEventEntity;
    }

    /**
     * delete calendarEventEntity, notificationEntity, assigneeMeetingEntity for input eventId
     * @param eventId
     */
    @Override
    public void deleteEvent(int eventId) {
        CalendarEventEntity calendarEventEntity = findCalendarEventEntityById(eventId);
        calendarRepository.delete(calendarEventEntity);
    }


    /**
     * get calendarEventEntity for input eventId
     * if no record is found, then RecordNotFoundException will be thrown which is gracefully shown
     *
     * @param eventId
     * @return
     */
    public CalendarEventEntity findCalendarEventEntityById(int eventId) {
        CalendarEventEntity calendarEventEntity = calendarRepository.findById(eventId).orElseThrow(
                ()->new RecordNotFoundException(CalendarConstant.ERROR_MESSAGE_VALUE_NO_RECORD));
        return calendarEventEntity;
    }

    /**
     * method to return Calendar event DTO against input eventId
     * RecordNotFoundException with graceful message will be returned if no record found for the input.
     * returned calendarEventDTO will have notification and assigneeMeeting DTOs as well
     * @param eventId
     * @return
     */
    @Override
    public CalendarEventDTO findEventById(int eventId) {
        CalendarEventEntity calendarEventEntity = findCalendarEventEntityById(eventId);
        CalendarEventDTO calendarEventDTO = getCalenderEvent(calendarEventEntity);
        return calendarEventDTO;
    }

    /**
     * method to return list of Calendar event DTO against input organiserEmail
     * RecordNotFoundException with graceful message will be returned if no record found for the input.
     * returned calendarEventDTO will have notification and assigneeMeeting DTOs as well
     * @param organiserEmail
     * @return
     */
    @Override
    public List<CalendarEventDTO> findEventsByOrganiserEmail(String organiserEmail) {
        List<CalendarEventDTO> calendarEventDTOList = new ArrayList<>();
        List<CalendarEventEntity> calendarEventEntityList = calendarRepository.findByOrganiserEmail(organiserEmail);
        if(calendarEventEntityList == null || calendarEventEntityList.isEmpty()){
            throw new RecordNotFoundException(CalendarConstant.ERROR_MESSAGE_VALUE_NO_RECORD);
        }else {
            calendarEventEntityList.stream().forEach(
                    calendarEventEntity -> {
                        CalendarEventDTO calendarEventDTO = getCalenderEvent(calendarEventEntity);
                        calendarEventDTOList.add(calendarEventDTO);
                    });
        }
        return calendarEventDTOList;
    }

    /**
     * Method to get CalendarEventDTO with notification and AssigneeMeetings
     * for input calendarEventEntity
     * @param calendarEventEntity
     * @return
     */
    public CalendarEventDTO getCalenderEvent(CalendarEventEntity calendarEventEntity){
        CalendarEventDTO calendarEventDTO = CalendarMapper.mapCalendarEntityToDto(calendarEventEntity);
        Set<AssigneeMeeting> assigneeMeetings = assigneeMeetingService.findAssigneeByCalendarEventEntity(calendarEventEntity);
        calendarEventDTO.setAssigneeMeetings(assigneeMeetings);
        Notification notification = notificationService.findNotificationByCalendarEventEntity(calendarEventEntity);
        calendarEventDTO.setNotification(notification);
        return calendarEventDTO;
    }

    /**
     * method to add assigneeMeeting for input eventId
     * if no record is present against either of the input,
     * RecordNotFoundException with graceful message will be returned.
     * returned calendarEventDTOs will have notification and assigneeMeeting DTOs as well
     * @param assigneeMeeting
     * @param eventId
     * @return
     */
    @Override
    public Set<AssigneeMeeting>  addAssigneeMeetings(AssigneeMeeting assigneeMeeting, int eventId) {
        CalendarEventEntity calendarEventEntity = findCalendarEventEntityById(eventId);
        Set<AssigneeMeeting> savedAllAssigneeMeetings = assigneeMeetingService.addAssigneeMeetingsByEventId(calendarEventEntity, assigneeMeeting);
        return savedAllAssigneeMeetings;
    }

    /**
     * update the AssigneeMeetingEntity for input eventId, assigneeEmail with input assigneeMeeting
     * @param eventId
     * @param assigneeEmail
     * @param assigneeMeeting
     * @return
     */
    @Override
    public AssigneeMeeting updateAssigneeMeeting(int eventId, String assigneeEmail, AssigneeMeeting assigneeMeeting) {
        CalendarEventEntity calendarEventEntity = findCalendarEventEntityById(eventId);
        AssigneeMeeting retAssigneeMeeting = assigneeMeetingService.updateAssigneeMeeting(calendarEventEntity, assigneeEmail, assigneeMeeting);
        return retAssigneeMeeting;
    }


}
