package org.example.mybatis_crud_exception_handling.repository;

import org.apache.ibatis.annotations.*;
import org.example.mybatis_crud_exception_handling.model.Attendee;
import org.example.mybatis_crud_exception_handling.model.dto.request.AttendeeRequest;

import java.util.List;

@Mapper
public interface AttendeeRepository {

    @Select("""
        INSERT INTO attendee (attendee_name, email)
        VALUES (#{attendee.attendeeName}, #{attendee.email})
        RETURNING *
    """)
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name")
    })
    Attendee saveAttendee(@Param("attendee") AttendeeRequest attendeeRequest);

    @Select("""
        SELECT * FROM attendee LIMIT #{limit} OFFSET #{offset}
    """)
    @ResultMap("attendeeMapper")
    List<Attendee> getAllAttendees(Integer offset, Integer limit);

    @Select("""
        SELECT * FROM attendee WHERE attendee_id = #{id}
    """)
    @ResultMap("attendeeMapper")
    Attendee getAttendeeById(Integer id);

    @Select("""
        DELETE FROM attendee WHERE attendee_id = #{id} RETURNING *
    """)
    @ResultMap("attendeeMapper")
    Attendee deleteAttendeeById(Integer id);

    @Select("""
        UPDATE attendee SET attendee_name = #{attendee.attendeeName},
        email = #{attendee.email} WHERE attendee_id = #{id} RETURNING *
    """)
    @ResultMap("attendeeMapper")
    Attendee updateAttendeeById(Integer id, @Param("attendee") AttendeeRequest attendeeRequest);

    @Select("""
        SELECT attendee.* FROM attendee INNER JOIN event_attendee
        ON attendee.attendee_id = event_attendee.attendee_id
        WHERE event_attendee.event_id = #{eventId};
    """)
    @ResultMap("attendeeMapper")
    List<Attendee> getAttendeesByEventId(Integer eventId);
}
