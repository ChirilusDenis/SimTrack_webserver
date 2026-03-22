package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.ChampionshipEntry;
import lombok.Getter;

import java.util.UUID;


@Getter
public class ChampionshipEntryResponseDto {
    private UUID id;
    private String driverName;
    private String championshipName;

    public ChampionshipEntryResponseDto(ChampionshipEntry championshipEntry) {
        this.id = championshipEntry.getId();
        this.driverName = championshipEntry.getUser().getUsername();
        this.championshipName = championshipEntry.getChampionship().getName();
    }

}
