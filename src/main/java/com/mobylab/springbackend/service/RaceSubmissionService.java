package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ApplicationStatus;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.enums.SubmissionStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.*;
import com.mobylab.springbackend.service.dto.LapDto;
import com.mobylab.springbackend.service.dto.RaceSubmissionDto;
import com.mobylab.springbackend.service.responseDto.RaceSubmissionResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class RaceSubmissionService {
    private final RaceSubmissionRepository raceSubmissionRepository;
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final LapService lapService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final ChampionshipEntryRepository championshipEntryRepository;
    private final LapRepository lapRepository;

    public RaceSubmission createSubmission(RaceSubmissionDto raceSubmissionDto,
                                           UUID driverId,
                                           UUID raceId) {
        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new BadRequestException("Race not found"));
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new BadRequestException("Driver not found"));

        if (race.getChampionship().getStatus() != ChampionshipStatus.ONGOING) {
            throw new BadRequestException("Championship has ended.");
        }

        // check if there is an approved entry with driver and championship
        if (!championshipEntryRepository
                .existsByChampionshipIdAndUserIdAndApplicationStatus(
                        race.getChampionship().getId()
                        , driverId,
                        ApplicationStatus.APPROVED
                )) {
            throw new BadRequestException("Driver has not been approved in this championship.");
        }

        String timeInput = raceSubmissionDto.getTime(); // mm:ss.SSS
        if (timeInput.split(":")[0].length() == 1) {
            timeInput = "0" + timeInput; // ensures two-digit minutes
        }
        LocalTime time = LocalTime.parse("00:" + timeInput, formatter);

        RaceSubmission raceSubmission = new RaceSubmission()
                .setRace(race)
                .setDriver(driver)
                .setVideoURL(raceSubmissionDto.getVideoURL())
                .setStatus(SubmissionStatus.PENDING)
                .setTime(time);

//        RaceSubmission raceSubmission;
//        Optional<RaceSubmission> maybeSub = raceSubmissionRepository
//                .findByRaceIdAndDriverId(raceId, driverId);
//
//        if (maybeSub.isPresent()) {
//            // update submission
//            raceSubmission = maybeSub.get();
//            // set time if better
//            if (time.isBefore(raceSubmission.getTime())) {
//                raceSubmission.setTime(time);
//            }
//        } else {
//            // create new submission
//            raceSubmission = new RaceSubmission()
//                    .setRace(race)
//                    .setDriver(driver)
//                    .setVideoURL(raceSubmissionDto.getVideoURL())
//                    .setStatus(SubmissionStatus.PENDING)
//                    .setTime(time);
//        }
        return raceSubmissionRepository.save(raceSubmission);
    }

    public List<RaceSubmission> getPending() {
        return raceSubmissionRepository.findByStatus(SubmissionStatus.PENDING);
    }

    public Lap validate(UUID submissionId, UUID validatedById) {
        RaceSubmission raceSubmission = raceSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new BadRequestException("Race submission not found"));
        User validateBy = userRepository.findById(validatedById)
                .orElseThrow(() -> new BadRequestException("Validator not found"));

        raceSubmission.setStatus(SubmissionStatus.VALIDATED);
//        System.out.println(raceSubmission.getTime());

        // Create or update lap with submission data
        Lap lap;

        Optional<Lap> maybeLap = lapRepository.findByRaceIdAndDriverId(
                raceSubmission.getRace().getId(),
                raceSubmission.getDriver().getId());

        if(maybeLap.isPresent()) {
            // update existing lap
            lap = maybeLap.get();
            // keep best time
            if (raceSubmission.getTime().isBefore(lap.getTime())) {
                lap.setTime(raceSubmission.getTime());
                lap.setVerifiedBy(validateBy);
            }
            lap = lapRepository.save(lap);
        } else {
            // create new lap
            LapDto lapDto = new LapDto()
                    .setTime(raceSubmission.getTime())
                    .setPoints(0)
                    .setRaceID(raceSubmission.getRace().getId())
                    .setDriverId(raceSubmission.getDriver().getId())
                    .setVerifiedById(validatedById);

            lap =  lapService.createLap(lapDto);
        }

        raceSubmissionRepository.save(raceSubmission);

        return lap;
    }

    public void reject(UUID sumissionId) {
        RaceSubmission raceSubmission = raceSubmissionRepository.findById(sumissionId)
                .orElseThrow(() -> new BadRequestException("Race submission not found"));

        raceSubmission.setStatus(SubmissionStatus.REJECTED);
        raceSubmissionRepository.save(raceSubmission);
    }

    public List<RaceSubmissionResponseDto> getByUserId(UUID raceId) {
        return raceSubmissionRepository.findByDriverId(raceId)
                .stream()
                .map(RaceSubmissionResponseDto::new)
                .toList();
    }
}
