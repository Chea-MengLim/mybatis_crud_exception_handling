
CREATE TABLE venue(
                      venue_id SERIAL PRIMARY KEY,
                      venue_name VARCHAR(255) NOT NULL ,
                      location VARCHAR(255) NOT NULL
);

CREATE TABLE event(
                      event_id SERIAL PRIMARY KEY,
                      event_name VARCHAR(50) NOT NULL ,
                      event_date timestamptz NOT NULL ,
                      venue_id INT NOT NULL ,
                      CONSTRAINT event_venue_id_fk
                          FOREIGN KEY (venue_id)
                              REFERENCES venue(venue_id)
                              ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE attendee(
                         attendee_id SERIAL PRIMARY KEY,
                         attendee_name VARCHAR(40) NOT NULL,
                         email VARCHAR(255) NOT NULL
);

CREATE TABLE event_attendee(
                               id SERIAL PRIMARY KEY,
                               event_id INT NOT NULL,
                               attendee_id INT NOT NULL,
                               CONSTRAINT event_id_event_attendee_fk
                                   FOREIGN KEY (event_id)
                                       REFERENCES event(event_id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT attendee_id_event_attendee_fk
                                   FOREIGN KEY (attendee_id)
                                       REFERENCES attendee(attendee_id)
                                       ON DELETE CASCADE ON UPDATE CASCADE
);