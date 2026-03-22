package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.ChampionshipRepository;
import com.mobylab.springbackend.repository.RaceRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.ChampionshipDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;

    public Championship addChampionship(ChampionshipDto championshipDto) {
        User createdBy = userRepository.findById(championshipDto.getCreatedById())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Championship championship = new Championship()
                .setName(championshipDto.getName())
                .setDescription(championshipDto.getDescription())
                .setCreatedBy(createdBy);

        return  championshipRepository.save(championship);
    }

    public void closeChampionship(UUID championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));

        if(!championship.getStatus().equals(ChampionshipStatus.ONGOING)) {
            throw new BadRequestException("Championship is not ongoing");
        }

        championship.setStatus(ChampionshipStatus.FINISHED);

        // close all races
//        List<Race> races = raceRepository.findByChampionshipId(championshipId);
//        for (Race race : races) {
//            race.setStatus(ChampionshipStatus.FINISHED);
//        }
//        raceRepository.saveAll(races);

        //TODO
        // Email with race results with race points
        // Maybe to all, maybe just to winners

        championshipRepository.save(championship);
    }

    public List<Championship> getOpenChampionships() {
        return championshipRepository.findByStatus(ChampionshipStatus.ONGOING);
    }

    //TODO
    // get total driver points
}
