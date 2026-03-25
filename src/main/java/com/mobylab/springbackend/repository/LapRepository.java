package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface LapRepository extends JpaRepository<Lap, UUID> {
    List<Lap> findByRaceAndDriver(Race race, User driver);
    Optional<Lap> findByRaceIdAndDriverId(UUID raceId, UUID driverId);

    List<Lap> findByRace(Race race);
    List<Lap> findByRaceId(UUID raceId);

    List<Lap> findByDriver(User driver);
    List<Lap> findByDriverId(UUID driverId);

    List<Lap> findByVerifiedBy(User verifiedBy);
    List<Lap> findByVerifiedById(UUID verifiedById);
}
