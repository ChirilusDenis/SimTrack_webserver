package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.ChampionshipEntry;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChampionshipEntryRepository extends JpaRepository<ChampionshipEntry, UUID> {
    List<ChampionshipEntry> findByChampionshipId(UUID championshipId);
    List<ChampionshipEntry> findByChampionship(Championship championship);

    List<ChampionshipEntry> findByUserId(UUID userId);
    List<ChampionshipEntry> findByUser(User user);

    List<ChampionshipEntry> findByChampionshipIdAndApplicationStatus(UUID championshipId, ApplicationStatus applicationStatus);
    List<ChampionshipEntry> findByChampionshipAndApplicationStatus(Championship championship, ApplicationStatus applicationStatus);

    List<ChampionshipEntry> findByApplicationStatus(ApplicationStatus applicationStatus);
    Boolean existsByChampionshipIdAndUserIdAndApplicationStatus(UUID championshipId, UUID userId, ApplicationStatus applicationStatus);
    Boolean existsByChampionshipIdAndApplicationStatus(UUID championshipId, ApplicationStatus applicationStatus);
}
