package com.mobylab.springbackend.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Component
public class LapDto {
    private LocalTime time;
    private int points;
    private UUID raceID;
    private UUID driverId;
    private UUID verifiedById;
}
