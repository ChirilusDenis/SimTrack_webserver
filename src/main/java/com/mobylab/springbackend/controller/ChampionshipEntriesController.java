package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.service.ChampionshipEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entries")
public class ChampionshipEntriesController implements SecuredRestController{

    @Autowired
    private ChampionshipEntryService championshipEntryService;

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<ChampionshipEntry>> getPendingEntries() {
        List<ChampionshipEntry> pendingEntries = championshipEntryService.getPendingEntries();
        return ResponseEntity.status(200).body(pendingEntries);
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> acceptEntry(@PathVariable UUID id) {
        championshipEntryService.aproveEntry(id);
        return ResponseEntity.status(200).build();
    }
    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<Void> rejectEntry(@PathVariable UUID id) {
        championshipEntryService.rejectEntry(id);
        return ResponseEntity.status(200).build();
    }

}
