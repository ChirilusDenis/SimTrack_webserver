package com.mobylab.springbackend.service.dto;

import com.mobylab.springbackend.entity.Championship;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Component
public class RaceDto {
    private String name;
    private String trackName;
    private UUID championshipId;
}
