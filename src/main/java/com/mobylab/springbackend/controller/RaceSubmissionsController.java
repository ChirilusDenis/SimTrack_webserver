package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.service.RaceSubmissionService;
import com.mobylab.springbackend.service.UserService;
import com.mobylab.springbackend.service.dto.RaceSubmissionDto;
import com.mobylab.springbackend.service.responseDto.LapResponseDto;
import com.mobylab.springbackend.service.responseDto.RaceSubmissionResponseDto;
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
    public ResponseEntity<RaceSubmissionResponseDto> submit(@PathVariable("id") UUID raceId,
                                                            Principal principal,
                                                            @RequestBody RaceSubmissionDto raceSubmissionDto) {
        String email = principal.getName();
        UUID driverId = userService.findByEmail(email).getId();
        RaceSubmission raceSubmission = raceSubmissionService.createSubmission(raceSubmissionDto, driverId, raceId);
        return ResponseEntity.status(201).body(new RaceSubmissionResponseDto(raceSubmission));

    }

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<RaceSubmissionResponseDto>> getAllSubmissions() {
        List<RaceSubmissionResponseDto> submissions = raceSubmissionService.getPending()
                .stream()
                .map(RaceSubmissionResponseDto::new)
                .toList();
        return ResponseEntity.status(200).body(submissions);
    }

    @PostMapping("/{id}/validate")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<LapResponseDto> validateRaceSubmission(@PathVariable("id") UUID submissionId,
                                                                 Principal principal) {
        String email = principal.getName();
        Lap lap = raceSubmissionService.validate(submissionId,
                userService.findByEmail(email).getId());
        return ResponseEntity.status(201).body(new LapResponseDto(lap));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> rejectRaceSubmission(@PathVariable("id") UUID submissionId) {
        raceSubmissionService.reject(submissionId);
        return ResponseEntity.status(200).build();
    }
}
