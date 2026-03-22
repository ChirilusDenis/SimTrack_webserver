package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.Race;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

//@Component
@Getter
public class RaceResponseDto {
    private UUID id;
    private String trackName;
    private String championshipName;

    public RaceResponseDto(Race race) {
        this.id = race.getId();
        this.trackName = race.getTrackName();
        this.championshipName = race.getChampionship().getName();
    }
}
