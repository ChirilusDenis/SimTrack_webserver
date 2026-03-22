package com.mobylab.springbackend.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@Accessors(chain = true)
public class RaceSubmissionDto {
    private long time;
    private String videoURL;
    private UUID driverId;
    private UUID raceId;
}
