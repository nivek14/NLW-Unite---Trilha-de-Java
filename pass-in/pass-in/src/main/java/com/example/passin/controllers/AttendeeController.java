package com.example.passin.controllers;

import com.example.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.example.passin.services.AttendeeService;
import com.example.passin.services.CheckinService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping("{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO attendeeBadgeResponseDTO = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(attendeeBadgeResponseDTO);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<Object> registerCheckin(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.checkInAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendee/{attendeeId}/badge").buildAndExpand(attendeeId).toString();
        return ResponseEntity.created(URI.create(uri)).build();
    }

}
