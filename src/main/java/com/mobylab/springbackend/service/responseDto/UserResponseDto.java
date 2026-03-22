package com.mobylab.springbackend.service.responseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@Accessors(chain = true)
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
}
