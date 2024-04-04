package com.example.passin.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeBadgeDTO {
    public String name;
    public String email;
    public String checkInUrl;
    public String eventId;
}
