package com.mobylab.springbackend.service.dto;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Accessors(chain = true)
@Component
public class ChampionshipEntryDto {
    private User user;
    private Championship championship;
    private ApplicationStatus applicationStatus;
}
