package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.Lap;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

//@Component
@Getter
public class LapResponseDto {
    private UUID id;
    private LocalTime time;
    private int points;
    private String driver;
    private String championship;
    private String trackName;
    private String verifiedBy;

    public  LapResponseDto(Lap lap) {
        this.id = lap.getId();
        this.time = lap.getTime();
        this.points = lap.getPoints();
        this.driver = lap.getDriver().getUsername();
        this.championship = lap.getRace().getChampionship().getName();
        this.trackName = lap.getRace().getTrackName();
        this.verifiedBy = lap.getVerifiedBy().getUsername();
    }
}
