package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.service.*;
import com.mobylab.springbackend.service.dto.ChampionshipDto;
import com.mobylab.springbackend.service.dto.RaceDto;
import com.mobylab.springbackend.service.responseDto.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/championships")
public class ChampionshipController implements  SecuredRestController {

    @Autowired
    private ChampionshipService championshipService;

    @Autowired
    private ChampionshipEntryService championshipEntryService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private UserService userService;

    @PostMapping
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<ChampionshipResponseDto> createChampionship(@RequestBody ChampionshipDto championshipDto,
                                                                      Principal principal) {
        String email = principal.getName();
        UUID userID = userService.findByEmail(email).getId();
        Championship championship = championshipService.addChampionship(championshipDto, userID);
        return ResponseEntity.status(201).body(new ChampionshipResponseDto(championship));
    }

    @GetMapping
    public ResponseEntity<List<ChampionshipResponseDto>> getOpenChampionships() {
        List<ChampionshipResponseDto> champs = championshipService
                .getOpenChampionships()
                .stream()
                .map(ChampionshipResponseDto::new)
                .toList();
        return ResponseEntity.status(200).body(champs);
    }

    @PostMapping("/{id}/apply")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ChampionshipEntryResponseDto> applyChampionship(@PathVariable("id") UUID championshipId,
                                                               Principal principal) {
        String email = principal.getName();
        UUID userID = userService.findByEmail(email).getId();
        ChampionshipEntry entry = championshipEntryService.applyToChampionship(championshipId, userID);
        return ResponseEntity.status(201).body(new ChampionshipEntryResponseDto(entry));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<UserResponseDto>> getParticipants(@PathVariable("id") UUID championshipId) {
        List<UserResponseDto> users = championshipService.getParticipantsDto(championshipId);
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/{id}/close")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> closeChampionship(@PathVariable("id") UUID championshipId) {
        championshipService.closeChampionship(championshipId);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/{id}/end")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<WinnerResponseDto>> endChampionship(@PathVariable("id") UUID championshipId) {
        List<WinnerResponseDto> winners = championshipService.endChampionship(championshipId);
        return ResponseEntity.status(200).body(winners);
    }

    @PostMapping("/{id}/race")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<RaceResponseDto> createRace(@PathVariable("id") UUID championshipId,
                                                      @RequestBody RaceDto raceDto) {
        Race race = raceService.createRace(raceDto, championshipId);
        return ResponseEntity.status(201).body(new RaceResponseDto(race));
    }

    @GetMapping("/{id}/races")
    public ResponseEntity<List<RaceResponseDto>> getRaces(@PathVariable("id") UUID championshipId) {
        List<RaceResponseDto> races = raceService.getChampionshipRaces(championshipId)
                .stream()
                .map(RaceResponseDto::new)
                .toList();
        return ResponseEntity.status(200).body(races);
    }

}
