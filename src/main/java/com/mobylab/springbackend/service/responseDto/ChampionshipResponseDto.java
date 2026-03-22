package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

//@Component
@Getter
public class ChampionshipResponseDto {
    private UUID id;
    private String name;
    private String description;
    private String racingClass;
    private ChampionshipStatus status;
    private String createdBy;

    public ChampionshipResponseDto(Championship championship) {
        id = championship.getId();
        name = championship.getName();
        description = championship.getDescription();
        racingClass = championship.getRacingClass();
        status = championship.getStatus();
        createdBy = championship.getCreatedBy().getUsername();
    }
}
