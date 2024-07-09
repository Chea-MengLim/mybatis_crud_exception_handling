package org.example.mybatis_crud_exception_handling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendee {
    private Integer attendeeId;
    private String attendeeName;
    private String email;
}
