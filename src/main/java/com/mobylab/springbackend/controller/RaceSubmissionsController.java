package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.service.RaceSubmissionService;
import com.mobylab.springbackend.service.UserService;
import com.mobylab.springbackend.service.dto.RaceSubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submissions")
public class RaceSubmissionsController {
    @Autowired
    private RaceSubmissionService raceSubmissionService;

    @Autowired
    private UserService  userService;

    @PostMapping("/race/{id}/submit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RaceSubmissionDto> submit(@PathVariable UUID raceId,
                                    Principal principal,
                                    @RequestBody RaceSubmissionDto raceSubmissionDto) {
        String email = principal.getName();

        raceSubmissionDto.setRaceId(raceId)
                .setDriverId(userService.findByEmail(email).getId());

        raceSubmissionService.createSubmission(raceSubmissionDto);
        return ResponseEntity.status(201).body(raceSubmissionDto);

    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<RaceSubmission>> getAllSubmissions() {
        List<RaceSubmission> submissions = raceSubmissionService.getPending();
        return ResponseEntity.status(200).body(submissions);
    }

    @PostMapping("/{id}/validate")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Lap> validateRaceSubmission(@PathVariable UUID submissionId,
                                                      Principal principal) {
        String email = principal.getName();
        Lap lap = raceSubmissionService.validate(submissionId,
                userService.findByEmail(email).getId());
        return ResponseEntity.status(201).body(lap);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> rejectRaceSubmission(@PathVariable UUID submissionId) {
        rejectRaceSubmission(submissionId);
        return ResponseEntity.status(200).build();
    }
}
