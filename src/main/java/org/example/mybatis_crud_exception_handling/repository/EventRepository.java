package org.example.mybatis_crud_exception_handling.repository;

import org.apache.ibatis.annotations.*;
import org.example.mybatis_crud_exception_handling.model.Event;
import org.example.mybatis_crud_exception_handling.model.dto.request.EventRequest;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface EventRepository {
    @Select("""
        SELECT * FROM event LIMIT #{limit} OFFSET #{offset}
    """)
    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
                    one = @One(select = "org.example.mybatis_crud_exception_handling.repository.VenueRepository.getVenueByID")),
            @Result(property = "attendees", column = "event_id",
                    many = @Many(select = "org.example.mybatis_crud_exception_handling.repository.AttendeeRepository.getAttendeesByEventId")),
    })
    List<Event> getAllEvents(Integer offset, Integer limit);

    @Select("""
        INSERT INTO event(event_name, event_date, venue_id)
        VALUES (#{event.eventName}, #{event.eventDate}, #{event.venueId})
        RETURNING event_id;
    """)
    Integer saveEvent(@Param("event") EventRequest eventRequest);

    @Insert("""
        INSERT INTO event_attendee(event_id, attendee_id)
        VALUES (#{eventId}, #{attendeeId})
    """)
    void saveEventAndAttendeeIntoEventAttendeeTable(Integer eventId, Integer attendeeId);

    @Select("""
        SELECT * FROM event WHERE event_id = #{eventId}
    """)
    @ResultMap("eventMapper")
    Event getEventById(Integer eventId);

    @Select("""
        DELETE FROM event WHERE event_id = #{id}
        RETURNING *;
    """)
    @ResultMap("eventMapper")
    Event deleteEventById(Integer id);

    @Update("""
        UPDATE event SET event_name = #{event.eventName}, event_date = #{event.eventDate}
        , venue_id = #{event.venueId}
        WHERE event_id = #{id}
    """)
    void updateEventById(Integer id, @Param("event") EventRequest eventRequest);

    @Delete("""
        DELETE FROM event_attendee WHERE event_id = #{id}
    """)
    void deleteEventAttendeeByEventId(Integer id);
}
