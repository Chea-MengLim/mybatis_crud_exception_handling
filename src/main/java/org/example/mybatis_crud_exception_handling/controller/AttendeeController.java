package org.example.mybatis_crud_exception_handling.controller;

import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Attendee;
import org.example.mybatis_crud_exception_handling.model.dto.request.AttendeeRequest;
import org.example.mybatis_crud_exception_handling.model.dto.response.APIResponse;
import org.example.mybatis_crud_exception_handling.service.AttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/attendees")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<APIResponse<Attendee>> saveAttendee(@RequestBody @Valid AttendeeRequest attendeeRequest){
        APIResponse<Attendee> response = APIResponse.<Attendee>builder()
                .message("The attendee has been successfully added.")
                .payload(attendeeService.saveAttendee(attendeeRequest))
                .status(HttpStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Attendee>>> getAllAttendees(
            @RequestParam (defaultValue = "1") @Positive Integer offset,
            @RequestParam (defaultValue = "3") @Positive Integer limit
            )
    {
        APIResponse<List<Attendee>> responses = APIResponse.<List<Attendee>>builder()
                .message("All attendees have been successfully fetched.")
                .payload(attendeeService.getAllAttendees(offset, limit))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Attendee>> getAttendeeById(@PathVariable @Positive Integer id){
        APIResponse<Attendee> response = APIResponse.<Attendee>builder()
                .message("The attendee has been successfully founded.")
                .payload(attendeeService.getAttendeeById(id))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Attendee>> deleteAttendeeById(@PathVariable @Positive Integer id){
        if(attendeeService.deleteAttendeeById(id) == null){
            throw new NotFoundException("The attendee id " + id + " has not been founded.");
        }
        APIResponse<Attendee> response = APIResponse.<Attendee>builder()
                .message("The attendee has been successfully deleted.")
                .payload(null)
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Attendee>> updateAttendeeById
            (@PathVariable @Positive Integer id, @RequestBody @Valid AttendeeRequest attendeeRequest){
        APIResponse<Attendee> response = APIResponse.<Attendee>builder()
                .message("The attendee has been successfully updated.")
                .payload(attendeeService.updateAttendeeById(id, attendeeRequest))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
