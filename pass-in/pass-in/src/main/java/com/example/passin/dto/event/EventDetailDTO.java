package com.example.passin.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailDTO {
    public String id;
    public String title;
    public String details;
    public String slug;
    public Integer maximumAttendees;
    public Integer attendeesAmount;
}
