package com.mobylab.springbackend.service.dto;

import com.mobylab.springbackend.entity.OrganizerApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerApplicationDto {
    private UUID id;
    private String username;
    private String email;

    public OrganizerApplicationDto(OrganizerApplication organizerApplication) {
        this.id = organizerApplication.getId();
        this.username = organizerApplication.getUsername();
        this.email = organizerApplication.getEmail();
    }
}
