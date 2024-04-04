package com.example.passin.config;

import com.example.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import com.example.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.example.passin.domain.checkin.exceptions.CheckinAlreadyExistsException;
import com.example.passin.domain.event.exceptions.EventFullException;
import com.example.passin.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeAlreadyRegistered(AttendeeAlreadyRegisteredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckinAlreadyExistsException.class)
    public ResponseEntity handleCheckinAlreadyExists(CheckinAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
