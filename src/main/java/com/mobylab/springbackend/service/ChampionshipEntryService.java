package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ApplicationStatus;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.ChampionshipEntryRepository;
import com.mobylab.springbackend.repository.ChampionshipRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.ChampionshipEntryDto;
import com.mobylab.springbackend.service.responseDto.ChampionshipEntryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ChampionshipEntryService {
    private final ChampionshipEntryRepository championshipEntryRepository;
    private final ChampionshipRepository championshipRepository;
    private final UserRepository userRepository;

    public ChampionshipEntry applyToChampionship(ChampionshipEntryDto championshipEntryDto) {
        if(championshipEntryDto.getChampionship().getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship is closed");
        }

        ChampionshipEntry championshipEntry = new ChampionshipEntry()
                .setUser(championshipEntryDto.getUser())
                .setChampionship(championshipEntryDto.getChampionship())
                .setApplicationStatus(ApplicationStatus.PENDING);
        return championshipEntryRepository.save(championshipEntry);
    }

    public ChampionshipEntry applyToChampionship(UUID championshipId, UUID userId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (championship.getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship is closed");
        }

        ChampionshipEntry championshipEntry = new ChampionshipEntry()
                .setUser(user)
                .setChampionship(championship)
                .setApplicationStatus(ApplicationStatus.PENDING);
        return championshipEntryRepository.save(championshipEntry);
    }

    public void approveEntry(UUID entryId) {
        ChampionshipEntry championshipEntry = championshipEntryRepository.findById(entryId)
                .orElseThrow(() -> new BadRequestException("Entry not found"));

        if ( championshipEntry.getApplicationStatus() != ApplicationStatus.PENDING) {
            throw new BadRequestException("This entry is not pending");
        }

        championshipEntry.setApplicationStatus(ApplicationStatus.APPROVED);
        championshipEntryRepository.save(championshipEntry);
    }

    public void rejectEntry(UUID entryId) {
        ChampionshipEntry championshipEntry = championshipEntryRepository.findById(entryId)
                .orElseThrow(() -> new BadRequestException("Entry not found"));

        if (championshipEntry.getApplicationStatus() != ApplicationStatus.PENDING) {
            throw new BadRequestException("This entry is not pending");
        }

        championshipEntry.setApplicationStatus(ApplicationStatus.REJECTED);
        championshipEntryRepository.save(championshipEntry);

//        championshipEntryRepository.delete(championshipEntry);
    }

    public List<ChampionshipEntry> getPendingEntries() {
        return championshipEntryRepository.findByApplicationStatus(ApplicationStatus.PENDING);
    }

    public List<ChampionshipEntryResponseDto> getByUserId(UUID userId) {
        return championshipEntryRepository.findByUserId(userId)
                .stream()
                .map(ChampionshipEntryResponseDto::new)
                .toList();
    }
}
