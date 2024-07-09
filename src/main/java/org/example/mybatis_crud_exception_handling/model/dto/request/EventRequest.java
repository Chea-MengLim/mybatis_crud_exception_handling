package org.example.mybatis_crud_exception_handling.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotNull
    @NotBlank
    private String eventName;
    @NotNull
    private LocalDateTime eventDate;
    @NotNull
    private Integer venueId;
    @NotNull
    private List<Integer> attendeesId;
}
