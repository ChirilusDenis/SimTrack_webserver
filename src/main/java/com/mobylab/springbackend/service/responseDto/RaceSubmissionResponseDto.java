package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.RaceSubmission;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

//@Component
@Getter
public class RaceSubmissionResponseDto {
    private UUID id;
    private String time;
    private String videoURL;
    private String driver;
    private String name;

    public RaceSubmissionResponseDto(RaceSubmission raceSubmission) {
        this.id = raceSubmission.getId();
        this.time = raceSubmission.getTime().toString();
        this.videoURL = raceSubmission.getVideoURL();
        this.driver = raceSubmission.getDriver().getUsername();
        this.name = raceSubmission.getRace().getName();
    }
}
