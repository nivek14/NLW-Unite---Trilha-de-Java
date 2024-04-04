package com.example.passin.services;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.event.Event;
import com.example.passin.domain.event.exceptions.EventFullException;
import com.example.passin.domain.event.exceptions.EventNotFoundException;
import com.example.passin.dto.attendee.AttendeeIdDTO;
import com.example.passin.dto.attendee.AttendeeRequestDTO;
import com.example.passin.dto.event.EventIdDTO;
import com.example.passin.dto.event.EventRequestDTO;
import com.example.passin.dto.event.EventResponseDTO;
import com.example.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequestDTO){

        Event newEvent = new Event();

        newEvent.setTitle(eventRequestDTO.title);
        newEvent.setDetails(eventRequestDTO.details);
        newEvent.setMaximumAttendees(eventRequestDTO.maximumAttendees);
        newEvent.setSlug(this.createSlug(eventRequestDTO.title));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    private AttendeeIdDTO registerAttendeeOnEvent(AttendeeRequestDTO attendeeRequestDTO ,String eventId){

        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email, eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Evento já está cheio");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name);
        newAttendee.setEmail(attendeeRequestDTO.email);
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId){
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not with ID:" + eventId));
        return event;
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized
                    .replaceAll("[\\p{InCOBINING_DIACRITICAL_MARKS}]", "")
                    .replaceAll("[^\\w\\s]", "")
                    .replaceAll("\\s+", "-")
                    .toLowerCase();
    }

}
