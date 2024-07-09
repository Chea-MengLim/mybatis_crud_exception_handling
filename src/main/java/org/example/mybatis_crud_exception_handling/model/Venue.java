package org.example.mybatis_crud_exception_handling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venue {
    private Integer venueId;
    private String venueName;
    private String location;
}
