package com.example.passin.services;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.checkin.CheckIn;
import com.example.passin.domain.checkin.exceptions.CheckinAlreadyExistsException;
import com.example.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckinService {

    private final CheckinRepository checkinRepository;

    public void checkInAttendee(Attendee attendee){
        this.verifyCheckInExists(attendee.getId());
        CheckIn checkin = new CheckIn();
        checkin.setAttendee(attendee);
        checkin.setCreatedAt(LocalDateTime.now());
        this.checkinRepository.save(checkin);
    }

    private void verifyCheckInExists(String id) {
        Optional<CheckIn> isChecked = this.checkinRepository.findByAttendeeId(id);
        if(isChecked.isPresent()) throw new CheckinAlreadyExistsException("checkin já realizado");
    }

}
