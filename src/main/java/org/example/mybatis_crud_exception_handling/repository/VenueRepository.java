package org.example.mybatis_crud_exception_handling.repository;

import org.apache.ibatis.annotations.*;
import org.example.mybatis_crud_exception_handling.model.Venue;
import org.example.mybatis_crud_exception_handling.model.dto.request.VenueRequest;

import java.util.List;

@Mapper
public interface VenueRepository {
    @Select("""
        INSERT INTO venue (venue_name, location)
        VALUES (#{venue.venueName}, #{venue.location})
        RETURNING *;
    """)
    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name")
    })
    Venue saveVenue(@Param("venue") VenueRequest venueRequest);

    @Select("SELECT * FROM venue LIMIT #{limit} OFFSET #{offset}")
    @ResultMap("venueMapper")
    List<Venue> getAllVenues(Integer offset, Integer limit);

    @Select("SELECT * FROM venue WHERE venue_id = #{id}")
    @ResultMap("venueMapper")
    Venue getVenueByID(Integer id);

    @Select("""
        UPDATE venue SET venue_name = #{venue.venueName},
                        location = #{venue.location}
                        WHERE venue_id = #{id}
                        RETURNING *;
    """)
    @ResultMap("venueMapper")
    Venue updateVenueByID(Integer id, @Param("venue") VenueRequest venueRequest);

    @Select("""
        DELETE FROM venue WHERE venue_id = #{id}
        RETURNING *;
    """)
    @ResultMap("venueMapper")
    Venue deleteVenueByID(Integer id);
}
