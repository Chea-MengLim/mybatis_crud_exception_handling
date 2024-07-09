package org.example.mybatis_crud_exception_handling.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Event;
import org.example.mybatis_crud_exception_handling.model.dto.request.EventRequest;
import org.example.mybatis_crud_exception_handling.model.dto.response.APIResponse;
import org.example.mybatis_crud_exception_handling.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<APIResponse<Event>> saveEvent(@RequestBody @Valid EventRequest eventRequest){
        APIResponse<Event> response = APIResponse.<Event>builder()
               .message("The event has been successfully added.")
               .payload(eventService.saveEvent(eventRequest))
               .status(HttpStatus.CREATED)
               .dateTime(LocalDateTime.now())
               .build()
                ;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Event>>> getAllEvents(
            @RequestParam (defaultValue = "1") @Positive Integer offset,
            @RequestParam(defaultValue = "3") @Positive Integer limit
    ){
        APIResponse<List<Event>> responses = APIResponse.<List<Event>>builder()
                .message("All events have been successfully fetched.")
                .payload(eventService.getAllEvents(offset, limit))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Event>> getEventById(@PathVariable @Positive Integer id){
        APIResponse<Event> response = APIResponse.<Event>builder()
               .message("The event has been successfully fetched.")
               .payload(eventService.getEventById(id))
               .status(HttpStatus.OK)
               .dateTime(LocalDateTime.now())
               .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Event>> deleteEventById(@PathVariable @Positive Integer id){
        if(eventService.deleteEventById(id) == null){
            throw new NotFoundException("The event id " + id + " has not been founded.");
        }
        APIResponse<Event> response = APIResponse.<Event>builder()
               .message("The event has been successfully deleted.")
               .payload(null)
               .status(HttpStatus.OK)
               .dateTime(LocalDateTime.now())
               .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Event>> updateEventById(@PathVariable @Positive Integer id, @RequestBody @Valid EventRequest eventRequest){
        APIResponse<Event> response = APIResponse.<Event>builder()
                .message("The event has been successfully updated")
                .payload(eventService.updateEventById(id, eventRequest))
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
