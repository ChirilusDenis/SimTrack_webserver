package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RaceRepository extends JpaRepository<Race, UUID> {
    List<Race> findByName(String name);
    List<Race> findByTrackname(String trackname);

    List<Race> findByChampionshipId(UUID championshipId);
    List<Race> findByChampionship(Championship championship);

}
