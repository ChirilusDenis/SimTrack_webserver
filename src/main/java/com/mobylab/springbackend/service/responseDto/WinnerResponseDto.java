package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.User;
import lombok.Getter;

@Getter
public class WinnerResponseDto extends UserResponseDto{
    private final int place;
    private final int points;

    public WinnerResponseDto(User user, int place, int points) {
        super(user);
        this.place = place;
        this.points = points;
    }
}
