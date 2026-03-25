package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Championship;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.ApplicationStatus;
import com.mobylab.springbackend.enums.ChampionshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChampionshipRepository extends JpaRepository<Championship, UUID> {
    List<Championship> findByStatus(ChampionshipStatus championshipStatus);
    List<Championship> findByName(String name);

    List<Championship> findByCreatedBy(User createdBy);
    List<Championship> findByCreatedById(UUID createdById);
}
