package org.example.mybatis_crud_exception_handling.service;

import org.example.mybatis_crud_exception_handling.model.Attendee;
import org.example.mybatis_crud_exception_handling.model.dto.request.AttendeeRequest;

import java.util.List;

public interface AttendeeService {
    Attendee saveAttendee(AttendeeRequest attendeeRequest);

    List<Attendee> getAllAttendees(Integer offset, Integer limit);

    Attendee getAttendeeById(Integer id);

    Attendee deleteAttendeeById(Integer id);

    Attendee updateAttendeeById(Integer id, AttendeeRequest attendeeRequest);
}
