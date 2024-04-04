package com.example.passin.services;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.checkin.CheckIn;
import com.example.passin.dto.attendee.AttendeeDetails;
import com.example.passin.dto.attendee.AttendeesListResponseDTO;
import com.example.passin.repositories.AttendeeRepository;
import com.example.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return attendeeList;
    }

    public AttendeesListResponseDTO getEventsAttendees(String eventId){

        // pega todos os participantes de um evento
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        // valida se para cada participante de um evento, possui checkin, se sim pega quando foi feito o checkin e senão retorna nulo
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).collect(Collectors.toList());

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

}
