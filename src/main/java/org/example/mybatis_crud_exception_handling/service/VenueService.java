package org.example.mybatis_crud_exception_handling.service;

import org.example.mybatis_crud_exception_handling.model.Venue;
import org.example.mybatis_crud_exception_handling.model.dto.request.VenueRequest;

import java.util.List;

public interface VenueService {

    Venue saveVenue(VenueRequest venueRequest);

    List<Venue> getAllVenues(Integer offset, Integer limit);

    Venue getVenueByID(Integer id);

    Venue updateVenueByID(Integer id, VenueRequest venueRequest);

    Venue deleteVenueByID(Integer id);
}
