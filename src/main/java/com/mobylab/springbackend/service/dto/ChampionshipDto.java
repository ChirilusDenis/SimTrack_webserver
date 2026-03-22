package com.mobylab.springbackend.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Component
public class ChampionshipDto {
    private String name;
    private String description;
    private String racingClass;
}
