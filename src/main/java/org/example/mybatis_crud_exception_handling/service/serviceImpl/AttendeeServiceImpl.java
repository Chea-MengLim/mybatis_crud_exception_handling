package org.example.mybatis_crud_exception_handling.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Attendee;
import org.example.mybatis_crud_exception_handling.model.dto.request.AttendeeRequest;
import org.example.mybatis_crud_exception_handling.repository.AttendeeRepository;
import org.example.mybatis_crud_exception_handling.service.AttendeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;

    @Override
    public Attendee saveAttendee(AttendeeRequest attendeeRequest) {
        return attendeeRepository.saveAttendee(attendeeRequest);
    }

    @Override
    public List<Attendee> getAllAttendees(Integer offset, Integer limit) {
        offset = (offset - 1) * limit;
        return attendeeRepository.getAllAttendees(offset, limit);
    }

    @Override
    public Attendee getAttendeeById(Integer id) {
        if(attendeeRepository.getAttendeeById(id) == null)
            throw new NotFoundException("The attendee id " + id + " does not exist");
        return attendeeRepository.getAttendeeById(id);
    }

    @Override
    public Attendee deleteAttendeeById(Integer id) {
        return attendeeRepository.deleteAttendeeById(id);
    }

    @Override
    public Attendee updateAttendeeById(Integer id, AttendeeRequest attendeeRequest) {
        if(attendeeRepository.updateAttendeeById(id, attendeeRequest) == null)
            throw new NotFoundException("The attendee id " + id + " does not exist");
        return attendeeRepository.updateAttendeeById(id, attendeeRequest);
    }
}
