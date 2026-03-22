package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.service.*;
import com.mobylab.springbackend.service.dto.ChampionshipDto;
import com.mobylab.springbackend.service.dto.RaceDto;
import com.mobylab.springbackend.service.dto.RaceSubmissionDto;
import com.mobylab.springbackend.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/championships")
public class ChampionshipController implements SecuredRestController {

    @Autowired
    private ChampionshipService championshipService;

    @Autowired
    private ChampionshipEntryService championshipEntryService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Championship> createChampionship(@RequestBody ChampionshipDto championshipDto,
                                                           Principal principal) {
        String email = principal.getName();
        UUID userID = userService.findByEmail(email).getId();
        championshipDto.setCreatedById(userID);
        Championship championship = championshipService.addChampionship(championshipDto);
        return ResponseEntity.status(201).body(championship);
    }

    @GetMapping
    public ResponseEntity<List<Championship>> getOpenChampionships() {
        List<Championship> champs = championshipService.getOpenChampionships();
        return ResponseEntity.status(200).body(champs);
    }

    @PostMapping("/{id}/apply")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChampionshipEntry> applyChampionship(@PathVariable("id") UUID championshipId,
                                                               Principal principal) {
        String email = principal.getName();
        UUID userID = userService.findByEmail(email).getId();
        ChampionshipEntry entry = championshipEntryService.applyToChampionship(championshipId, userID);
        return ResponseEntity.status(201).body(entry);
    }

    @PostMapping("/{id}/participants")
    public ResponseEntity<List<UserDto>> getParticipants(@PathVariable("id") UUID championshipId) {
        List<UserDto> users = championshipEntryService.getParticipants(championshipId);
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/{id}/close")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> closeChampionship(@PathVariable("id") UUID championshipId) {
        championshipService.closeChampionship(championshipId);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/{id}/race")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Race> createRace(@PathVariable("id") UUID championshipId,
                                           @RequestBody RaceDto raceDto) {
        raceDto.setChampionshipId(championshipId);
        Race race = raceService.createRace(raceDto);
        return ResponseEntity.status(201).body(race);
    }

    @GetMapping("/{id}/races")
    //TODO
    // Maybe replace with details about races
    // With just championship name
    public ResponseEntity<List<Race>> getRaces(@PathVariable("id") UUID championshipId) {
        List<Race> races = raceService.getChampionshipRaces(championshipId);
        return ResponseEntity.status(200).body(races);
    }

}
