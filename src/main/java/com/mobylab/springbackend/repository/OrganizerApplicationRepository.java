package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.OrganizerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizerApplicationRepository extends JpaRepository<OrganizerApplication, Long> {
    public Optional<OrganizerApplication> findById(UUID id);
    public List<OrganizerApplication>  findAllByApprovedByIsNull();
}
