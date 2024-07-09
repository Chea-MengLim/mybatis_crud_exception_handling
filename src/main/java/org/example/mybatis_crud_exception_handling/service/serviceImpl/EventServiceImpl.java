package org.example.mybatis_crud_exception_handling.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Event;
import org.example.mybatis_crud_exception_handling.model.dto.request.EventRequest;
import org.example.mybatis_crud_exception_handling.repository.AttendeeRepository;
import org.example.mybatis_crud_exception_handling.repository.EventRepository;
import org.example.mybatis_crud_exception_handling.repository.VenueRepository;
import org.example.mybatis_crud_exception_handling.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final AttendeeRepository attendeeRepository;

    @Override
    public Event saveEvent(EventRequest eventRequest) {
        // check venue by venue id
        if(venueRepository.getVenueByID(eventRequest.getVenueId()) == null)
            throw new NotFoundException("venue id " + eventRequest.getVenueId() + " is not found");
        // check attendee by attendee id
        for(Integer attendeeId : eventRequest.getAttendeesId()){
            if(attendeeRepository.getAttendeeById(attendeeId) == null){
                throw new NotFoundException("attendee id " + attendeeId + " is not found");
            }
        }
        Integer eventId = eventRepository.saveEvent(eventRequest);
        for(Integer attendeeId : eventRequest.getAttendeesId()){
            eventRepository.saveEventAndAttendeeIntoEventAttendeeTable(eventId, attendeeId);
        }
        return eventRepository.getEventById(eventId);
    }

    @Override
    public List<Event> getAllEvents(Integer offset, Integer limit) {
        offset = (offset - 1) * limit;
        return eventRepository.getAllEvents(offset, limit);
    }

    @Override
    public Event getEventById(Integer id) {
        if(eventRepository.getEventById(id) == null)
            throw new NotFoundException("event id " + id + " is not found");
        return eventRepository.getEventById(id);
    }

    @Override
    public Event deleteEventById(Integer id) {
        return eventRepository.deleteEventById(id);
    }

    @Override
    public Event updateEventById(Integer id, EventRequest eventRequest) {
        // validate before updating
        // check event by event id
        if(eventRepository.getEventById(id) == null)
            throw new NotFoundException("event id " + id + " is not found");
        // check venue by venue id
        if(venueRepository.getVenueByID(eventRequest.getVenueId()) == null)
            throw new NotFoundException("venue id " + eventRequest.getVenueId() + " is not found");
        for(Integer attendeeId : eventRequest.getAttendeesId()){
            // check attendee by attendee id
            if(attendeeRepository.getAttendeeById(attendeeId) == null)
                throw new NotFoundException("attendee id " + attendeeId + " is not found");
        }
        eventRepository.updateEventById(id, eventRequest);
        // delete data from event_attendee by event_id
        eventRepository.deleteEventAttendeeByEventId(id);
        // insert data into event_attendee
        for(Integer attendeeId : eventRequest.getAttendeesId()){
            eventRepository.saveEventAndAttendeeIntoEventAttendeeTable(id, attendeeId);
        }
        return eventRepository.getEventById(id);
    }
}
