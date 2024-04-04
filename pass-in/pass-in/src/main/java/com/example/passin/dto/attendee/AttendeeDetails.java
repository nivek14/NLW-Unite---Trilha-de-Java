package com.example.passin.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeDetails {
    public String id;
    public String name;
    public String email;
    public LocalDateTime createdAt;
    public LocalDateTime checkedInAt;
}
