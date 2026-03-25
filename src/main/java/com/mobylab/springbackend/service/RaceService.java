package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.Race;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.ChampionshipRepository;
import com.mobylab.springbackend.repository.RaceRepository;
import com.mobylab.springbackend.service.dto.RaceDto;
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
public class RaceService {

    private final RaceRepository raceRepository;
    private final ChampionshipRepository championshipRepository;

    public Race createRace(RaceDto raceDto, UUID championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new BadRequestException("Championship not found"));

        Race race = new Race()
                .setName(raceDto.getName())
                .setTrackName(raceDto.getTrackName())
                .setChampionship(championship);
        return raceRepository.save(race);
    }

    public List<Race> getChampionshipRaces(UUID championshipId) {
        if(!championshipRepository.existsById(championshipId)) {
            throw new BadRequestException("Championship not found");
        }

//        return raceRepository.findByChampionshipId(championshipId);
        return raceRepository.findByChampionshipId(championshipId);
    }
}
