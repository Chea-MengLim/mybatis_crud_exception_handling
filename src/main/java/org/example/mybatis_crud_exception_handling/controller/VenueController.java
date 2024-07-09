package org.example.mybatis_crud_exception_handling.controller;


import jakarta.validation.Valid;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Venue;
import org.example.mybatis_crud_exception_handling.model.dto.request.VenueRequest;
import org.example.mybatis_crud_exception_handling.model.dto.response.APIResponse;
import org.example.mybatis_crud_exception_handling.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/venue")
@AllArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @PostMapping
    ResponseEntity<APIResponse<Venue>> saveVenue(@RequestBody @Valid VenueRequest venueRequest){
        APIResponse<Venue> response = APIResponse.<Venue>builder()
               .message("Insert Venue Successful")
               .payload(venueService.saveVenue(venueRequest))
               .status(HttpStatus.CREATED)
               .dateTime(LocalDateTime.now())
               .build()
               ;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<APIResponse<List<Venue>>> getAllVenues(
            @RequestParam (defaultValue = "1") @Positive Integer offset,
            @RequestParam (defaultValue = "2") @Positive Integer limit
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "All venues have been successfully fetched.",
                        venueService.getAllVenues(offset, limit),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Venue>> getVenueByID(@PathVariable @Positive Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "The venue has been successfully founded.",
                        venueService.getVenueByID(id),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Venue>> updateVenueByID(
            @PathVariable @Positive Integer id,
            @RequestBody @Valid VenueRequest venueRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "The venue has been successfully updated.",
                        venueService.updateVenueByID(id, venueRequest),
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Object>> deleteVenueByID(@PathVariable @Positive Integer id){
        if(venueService.deleteVenueByID(id) == null) {
            throw new NotFoundException("The venue id " + id + " has not been founded.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "The venue has been successfully deleted.",
                        null,
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }
}
