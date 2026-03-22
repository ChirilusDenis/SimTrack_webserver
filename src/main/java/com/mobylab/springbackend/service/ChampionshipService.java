package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.*;
import com.mobylab.springbackend.enums.ApplicationStatus;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.ChampionshipEntryRepository;
import com.mobylab.springbackend.repository.ChampionshipRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.ChampionshipDto;
import com.mobylab.springbackend.service.responseDto.UserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final UserRepository userRepository;
    private final ChampionshipEntryRepository championshipEntryRepository;

    public Championship addChampionship(ChampionshipDto championshipDto, UUID creatorID) {
        User createdBy = userRepository.findById(creatorID)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Championship championship = new Championship()
                .setName(championshipDto.getName())
                .setDescription(championshipDto.getDescription())
                .setCreatedBy(createdBy)
                .setRacingClass(championshipDto.getRacingClass());

        return  championshipRepository.save(championship);
    }

    public void closeChampionship(UUID championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));

        if(!championship.getStatus().equals(ChampionshipStatus.ONGOING)) {
            throw new BadRequestException("Championship is not ongoing");
        }

        championship.setStatus(ChampionshipStatus.FINISHED);

        //TODO
        // Email with race results with race points
        // Maybe to all, maybe just to winners

        championshipRepository.save(championship);
    }

    public List<UserResponseDto> getParticipantsDto(UUID championshipId) {
        Optional<Championship> maybeChamp = championshipRepository.findById(championshipId);
        if(maybeChamp.isEmpty() || maybeChamp.get().getStatus() == ChampionshipStatus.FINISHED) {
            throw new BadRequestException("Championship not found");
        }

        List<UserResponseDto> users = championshipEntryRepository
                .findByChampionshipIdAndApplicationStatus(championshipId,
                        ApplicationStatus.APPROVED)
                .stream()
                .map(entry -> {
                    User user = entry.getUser();
                    return new UserResponseDto()
                            .setUsername(user.getUsername())
                            .setEmail(user.getEmail())
                            .setId(user.getId());
                })
                .distinct()
                .toList();
        return users;
    }

    public List<User> getParticipants(UUID championshipId) {
        Optional<Championship> maybeChamp = championshipRepository.findById(championshipId);
        if(maybeChamp.isEmpty() || maybeChamp.get().getStatus() == ChampionshipStatus.FINISHED) {
            throw new BadRequestException("Championship not found");
        }

        List<User> users = championshipEntryRepository
                .findByChampionshipIdAndApplicationStatus(championshipId,
                        ApplicationStatus.APPROVED)
                .stream()
                .map(ChampionshipEntry::getUser)
                .toList();

        return users;
    }

    public List<Championship> getOpenChampionships() {
        return championshipRepository.findByStatus(ChampionshipStatus.ONGOING);
    }
}
