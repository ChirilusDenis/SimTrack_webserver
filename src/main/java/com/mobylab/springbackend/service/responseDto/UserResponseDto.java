package com.mobylab.springbackend.service.responseDto;

import com.mobylab.springbackend.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Accessors(chain = true)
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;

    public UserResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
