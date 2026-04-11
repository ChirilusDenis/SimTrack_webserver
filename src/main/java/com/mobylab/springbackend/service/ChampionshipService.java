package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.*;
import com.mobylab.springbackend.enums.ApplicationStatus;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.enums.SubmissionStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.*;
import com.mobylab.springbackend.service.dto.ChampionshipDto;
import com.mobylab.springbackend.service.responseDto.UserResponseDto;
import com.mobylab.springbackend.service.responseDto.WinnerResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

//import com.mobylab.springbackend.service.EmailService;

@RequiredArgsConstructor
@Service
@Transactional
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final UserRepository userRepository;
    private final ChampionshipEntryRepository championshipEntryRepository;
    private final RaceRepository raceRepository;
    private final LapRepository lapRepository;
    private final RaceSubmissionRepository raceSubmissionRepository;
    private final EmailService emailService;

    public Championship addChampionship(ChampionshipDto championshipDto, UUID creatorID) {
        User createdBy = userRepository.findById(creatorID)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Championship championship = new Championship()
                .setName(championshipDto.getName())
                .setDescription(championshipDto.getDescription())
                .setCreatedBy(createdBy)
                .setRacingClass(championshipDto.getRacingClass())
                .setStatus(ChampionshipStatus.ONGOING);

        return  championshipRepository.save(championship);
    }

    public void closeChampionship(UUID championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));

        if( championship.getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship is not ongoing");
        }

        championship.setStatus(ChampionshipStatus.CLOSED);

        championshipRepository.save(championship);
    }

    public List<UserResponseDto> getParticipantsDto(UUID championshipId) {
        Optional<Championship> maybeChamp = championshipRepository.findById(championshipId);
        if(maybeChamp.isEmpty() || maybeChamp.get().getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship not found");
        }

        return championshipEntryRepository
                .findByChampionshipIdAndApplicationStatus(championshipId,
                        ApplicationStatus.APPROVED)
                .stream()
                .map(
                        entry -> new UserResponseDto(entry.getUser())
                )
                .distinct()
                .toList();
    }

    public List<User> getParticipants(UUID championshipId) {
        Optional<Championship> maybeChamp = championshipRepository.findById(championshipId);
        if(maybeChamp.isEmpty()) {
            throw new BadRequestException("Championship not found");
        }

        return championshipEntryRepository
                .findByChampionshipIdAndApplicationStatus(championshipId,
                        ApplicationStatus.APPROVED)
                .stream()
                .map(ChampionshipEntry::getUser)
                .toList();
    }

    public List<Championship> getOpenChampionships() {
        return championshipRepository.findByStatus(ChampionshipStatus.ONGOING);
    }

    public int getPointsInChampionship(UUID championshipId, UUID driverId) {
        int points = 0;
        User driver  = userRepository.findById(driverId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!championshipRepository.existsById(championshipId)) {
            throw new BadRequestException("Championship not found");
        }

        for(Lap lap : driver.getDrivenLaps()) {
            if(lap.getRace().getChampionship().getId().equals(championshipId)) {
                points += lap.getPoints();
            }
        }
        return points;
    }

    public List<WinnerResponseDto> endChampionship(UUID championshipId) {

        // Make sure dependencies are met
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));

        if (championship.getStatus() != ChampionshipStatus.CLOSED) {
            throw new BadRequestException("Championship should closed for submissions");
        }

        if (championshipEntryRepository.existsByChampionshipIdAndApplicationStatus(
                championshipId, ApplicationStatus.PENDING)) {
            throw new BadRequestException("Some entries still pending");
        }


        // Find unmarked submissions
        List<Race> championshipRaces = raceRepository.findByChampionshipId(championshipId);

        for(Race race : championshipRaces) {
            if(raceSubmissionRepository.existsByRaceIdAndStatus(race.getId(), SubmissionStatus.PENDING)) {
                throw new BadRequestException("Some submissions still pending");
            }
        }

        championship.setStatus(ChampionshipStatus.FINISHED);
        championshipRepository.save(championship);

        // Accord points based on points
        for (Race race : championshipRaces) {
            List<Lap> raceLaps = lapRepository.findByRaceId(race.getId());
            raceLaps.sort(Comparator.comparing(Lap::getTime));

            raceLaps.get(0).setPoints(25);
            if (raceLaps.size() > 1) {
                raceLaps.get(1).setPoints(18);
            }
            if (raceLaps.size() > 2) {
                raceLaps.get(2).setPoints(9);
            }

            lapRepository.saveAll(raceLaps);
        }

        // Get top 3 drivers in championship
        List<User> participants = new ArrayList<>(getParticipants(championshipId));
        participants.sort((u1, u2) ->
                getPointsInChampionship(championshipId, u2.getId())
                        - getPointsInChampionship(championshipId, u1.getId()));

        List<WinnerResponseDto> winners = new ArrayList<>();
        for (int i = 0; i < 3 && i < participants.size(); i++) {

            String place = switch (i){
                case 0 -> "first";
                case 1 -> "second";
                default -> "third";
            };
            emailService.sendSimpleMessage(participants.get(i).getEmail(),
                    "Standings in " + championship.getName() + " championship",
                    "You got " + place + " place.\n");

            winners.add(new WinnerResponseDto(participants.get(i),
                    i + 1,
                    getPointsInChampionship(championshipId, participants.get(i).getId())));
        }

        return winners;
    }
}
