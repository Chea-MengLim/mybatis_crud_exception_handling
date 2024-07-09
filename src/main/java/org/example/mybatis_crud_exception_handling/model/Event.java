package org.example.mybatis_crud_exception_handling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Integer eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private Venue venue;
    private List<Attendee> attendees;
}
