package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.Lap;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.ChampionshipRepository;
import com.mobylab.springbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ChampionshipRepository championshipRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));
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
}
