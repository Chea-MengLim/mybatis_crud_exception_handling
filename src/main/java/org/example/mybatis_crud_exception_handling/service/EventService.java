package org.example.mybatis_crud_exception_handling.service;

import org.example.mybatis_crud_exception_handling.model.Event;
import org.example.mybatis_crud_exception_handling.model.dto.request.EventRequest;

import java.util.List;

public interface EventService {
    Event saveEvent(EventRequest eventRequest);

    List<Event> getAllEvents(Integer offset, Integer limit);

    Event getEventById(Integer id);

    Event deleteEventById(Integer id);

    Event updateEventById(Integer id, EventRequest eventRequest);
}
