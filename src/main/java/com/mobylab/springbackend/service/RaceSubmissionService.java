package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.enums.SubmissionStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.RaceRepository;
import com.mobylab.springbackend.repository.RaceSubmissionRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.LapDto;
import com.mobylab.springbackend.service.dto.RaceSubmissionDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RaceSubmissionService {
    private final RaceSubmissionRepository raceSubmissionRepository;
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final LapService lapService;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss.SS");

    public RaceSubmission createSubmission(RaceSubmissionDto raceSubmissionDto,
                                           UUID driverId,
                                           UUID raceId) {
        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new BadRequestException("Race not found"));
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new BadRequestException("Driver not found"));

        if(race.getChampionship().getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship has ended.");
        }

        //TODO
        // Make sure there aren't duplicate submissions
        // Same driver and race

        RaceSubmission raceSubmission = new RaceSubmission()
                .setRace(race)
                .setDriver(driver)
                .setVideoURL(raceSubmissionDto.getVideoURL())
                .setStatus(SubmissionStatus.PENDING)
                .setTime(LocalTime.parse(raceSubmissionDto.getTime(), dtf));

        return raceSubmissionRepository.save(raceSubmission);
    }

    public List<RaceSubmission> getPending() {
        return raceSubmissionRepository.findByStatus(SubmissionStatus.PENDING);
    }

    public Lap validate(UUID sumissionId, UUID validatedById) {
        RaceSubmission raceSubmission = raceSubmissionRepository.findById(sumissionId)
                .orElseThrow(() -> new BadRequestException("Race submission not found"));

        raceSubmission.setStatus(SubmissionStatus.VALIDATED);

        raceSubmissionRepository.save(raceSubmission);

        // Create lap with submission data
        //TODO
        // Make sure no duplicate laps
        // Same driver and race
        LapDto lapDto = new LapDto()
                .setTime(raceSubmission.getTime())
                .setPoints(0)
                .setRaceID(raceSubmission.getRace().getId())
                .setDriverId(raceSubmission.getDriver().getId())
                .setVerifiedById(validatedById);

        return lapService.createLap(lapDto);

    }
    public void reject(UUID sumissionId) {
        RaceSubmission raceSubmission = raceSubmissionRepository.findById(sumissionId)
                .orElseThrow(() -> new BadRequestException("Race submission not found"));

        raceSubmission.setStatus(SubmissionStatus.REJECTED);
        raceSubmissionRepository.save(raceSubmission);
    }
}
