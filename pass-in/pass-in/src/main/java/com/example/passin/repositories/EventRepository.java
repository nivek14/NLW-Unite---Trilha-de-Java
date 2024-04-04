package com.example.passin.repositories;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
}
