package com.example.passin.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendeesListResponseDTO {
    List<AttendeeDetails> attendeeDetailsList;
}
