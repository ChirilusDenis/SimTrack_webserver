package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.enums.ApplicationStatus;
import lombok.Getter;

import java.util.UUID;


@Getter
public class ChampionshipEntryResponseDto {
    private UUID id;
    private String driverName;
    private String championshipName;
    private ApplicationStatus status;

    public ChampionshipEntryResponseDto(ChampionshipEntry championshipEntry) {
        this.id = championshipEntry.getId();
        this.driverName = championshipEntry.getUser().getUsername();
        this.championshipName = championshipEntry.getChampionship().getName();
        this.status = championshipEntry.getApplicationStatus();
    }

}
