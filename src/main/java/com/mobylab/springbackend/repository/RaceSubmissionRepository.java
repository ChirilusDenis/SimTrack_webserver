package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.RaceSubmission;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaceSubmissionRepository extends JpaRepository<RaceSubmission, UUID> {
    List<RaceSubmission> findByStatus(SubmissionStatus status);

    List<RaceSubmission> findByRace(Race race);
    List<RaceSubmission> findByRaceId(UUID raceId);

    List<RaceSubmission> findByDriver(User driver);
    List<RaceSubmission> findByDriverId(UUID driverId);

    Optional<RaceSubmission> findByRaceIdAndDriverId(UUID raceId, UUID driverId);

    Boolean existsByRaceIdAndStatus(UUID raceId, SubmissionStatus status);
}
