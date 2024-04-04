package com.example.passin.services;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import com.example.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.example.passin.domain.checkin.CheckIn;
import com.example.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.example.passin.dto.attendee.AttendeeDetails;
import com.example.passin.dto.attendee.AttendeesListResponseDTO;
import com.example.passin.dto.attendee.AttendeeBadgeDTO;
import com.example.passin.repositories.AttendeeRepository;
import com.example.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;
    private final CheckinService checkinService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return attendeeList;
    }

    public AttendeesListResponseDTO getEventsAttendees(String eventId){

        // pega todos os participantes de um evento
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        // valida se para cada participante de um evento, possui checkin, se sim pega quando foi feito o checkin e senão retorna nulo
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).collect(Collectors.toList());

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw  new AttendeeAlreadyRegisteredException("participante já cadastrado");
    }

    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){

        Attendee attendee = this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("attendee not found with id:" + attendeeId));

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkinService.checkInAttendee(attendee);
    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("attendee not found with id:" + attendeeId));
    }

}
