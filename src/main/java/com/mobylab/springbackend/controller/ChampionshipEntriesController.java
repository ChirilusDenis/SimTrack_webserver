package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.service.ChampionshipEntryService;
import com.mobylab.springbackend.service.UserService;
import com.mobylab.springbackend.service.responseDto.ChampionshipEntryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entries")
public class ChampionshipEntriesController implements SecuredRestController{

    @Autowired
    private ChampionshipEntryService championshipEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/pending")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<ChampionshipEntryResponseDto>> getPendingEntries() {
        List<ChampionshipEntryResponseDto> pendingEntries = championshipEntryService
                .getPendingEntries()
                .stream()
                .map(ChampionshipEntryResponseDto::new)
                .toList();
        return ResponseEntity.status(200).body(pendingEntries);
    }

    @PostMapping("/{id}/accept")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> acceptEntry(@PathVariable("id") UUID id) {
        championshipEntryService.approveEntry(id);
        return ResponseEntity.status(200).build();
    }
    @PostMapping("/{id}/reject")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> rejectEntry(@PathVariable("id") UUID id) {
        championshipEntryService.rejectEntry(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/mine")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<ChampionshipEntryResponseDto>> getMyEntries(Principal principal) {
        String email = principal.getName();
        List<ChampionshipEntryResponseDto> entries = championshipEntryService
                .getByUserId(userService.findByEmail(email).getId());
        return ResponseEntity.status(200).body(entries);
    }
}
