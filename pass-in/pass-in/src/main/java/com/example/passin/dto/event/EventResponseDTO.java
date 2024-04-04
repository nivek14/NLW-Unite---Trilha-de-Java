package com.example.passin.dto.event;

import com.example.passin.domain.event.Event;

public class EventResponseDTO {

    private EventDetailDTO eventDetailDTO;

    public EventResponseDTO(Event event, Integer numberOfAttendees){
        this.eventDetailDTO = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);
    }

}
