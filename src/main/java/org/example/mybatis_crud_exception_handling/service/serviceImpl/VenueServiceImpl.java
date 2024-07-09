package org.example.mybatis_crud_exception_handling.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.mybatis_crud_exception_handling.exception.NotFoundException;
import org.example.mybatis_crud_exception_handling.model.Venue;
import org.example.mybatis_crud_exception_handling.model.dto.request.VenueRequest;
import org.example.mybatis_crud_exception_handling.repository.VenueRepository;
import org.example.mybatis_crud_exception_handling.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;


    @Override
    public Venue saveVenue(VenueRequest venueRequest) {
        return venueRepository.saveVenue(venueRequest);
    }

    @Override
    public List<Venue> getAllVenues(Integer offset, Integer limit) {
        offset = (offset - 1) * limit;
        return venueRepository.getAllVenues(offset, limit);
    }

    @Override
    public Venue getVenueByID(Integer id) {
        Venue venue = venueRepository.getVenueByID(id);
        if (venue == null) {
            throw new NotFoundException("The venue id " + id + " has not been founded.");
        }
        return venue;
    }

    @Override
    public Venue updateVenueByID(Integer id, VenueRequest venueRequest) {
        if(venueRepository.updateVenueByID(id, venueRequest) == null) {
            throw new NotFoundException("The venue id " + id + " has not been founded.");
        }
        return venueRepository.updateVenueByID(id, venueRequest);
    }

    @Override
    public Venue deleteVenueByID(Integer id) {
        return venueRepository.deleteVenueByID(id);
    }
}
