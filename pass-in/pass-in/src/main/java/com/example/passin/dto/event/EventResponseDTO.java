package com.example.passin.dto.event;

import com.example.passin.domain.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {

    private EventDetailDTO eventDetailDTO;

    public EventResponseDTO(Event event, Integer numberOfAttendees){
        this.eventDetailDTO = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);
    }

}
