package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.OrganizerApplicationService;
import com.mobylab.springbackend.service.UserService;
import com.mobylab.springbackend.service.dto.OrganizerApplicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasAuthority('ORGANIZER')")
@RequestMapping("/organizer_applications")
public class OrganizerApplicationController implements SecuredRestController{
    @Autowired
    private OrganizerApplicationService organizerApplicationService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<OrganizerApplicationDto>> getOrganizerApplications(){
        List<OrganizerApplicationDto> appls = organizerApplicationService.getAllPending();
        return ResponseEntity.status(200).body(appls);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<String> acceptOrganizerApplication(UUID id, Principal principal){
        String email = principal.getName();
        UUID userID = userService.findByEmail(email).getId();

        organizerApplicationService.accept(id, userID);
        return ResponseEntity.status(201).body("User registered as organizer.");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectOrganizerApplication(UUID id){
        organizerApplicationService.deny(id);
        return ResponseEntity.status(200).body("User denied as organizer.");
    }

}
