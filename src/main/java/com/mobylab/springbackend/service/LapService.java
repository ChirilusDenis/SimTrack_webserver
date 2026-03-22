package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.LapRepository;
import com.mobylab.springbackend.repository.RaceRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.LapDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class LapService {
    private final LapRepository lapRepository;
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;

    public Lap createLap(LapDto lapDto) {
        User driver = userRepository.findById(lapDto.getDriverId())
                .orElseThrow(() -> new BadRequestException("Driver not found"));
        User verifiedBy = userRepository.findById(lapDto.getVerifiedById())
                .orElseThrow(() -> new BadRequestException("User not found"));
        Race race = raceRepository.findById(lapDto.getRaceID())
                .orElseThrow(() -> new BadRequestException("Race not found"));

        Lap lap = new Lap()
                .setTime(lapDto.getTime())
                .setPoints(lapDto.getPoints())
                .setDriver(driver)
                .setRace(race)
                .setVerifiedBy(verifiedBy);
        return lapRepository.save(lap);
    }

    public void addPoints(UUID lapId, int points) {
        Lap lap = lapRepository.findById(lapId)
                .orElseThrow(() -> new BadRequestException("Lap not found"));
        lap.setPoints(points);
        lapRepository.save(lap);
    }
}
