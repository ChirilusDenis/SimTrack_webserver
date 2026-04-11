package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.OrganizerApplication;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.UserRole;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.OrganizerApplicationRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.OrganizerApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class OrganizerApplicationService {

    private final OrganizerApplicationRepository organizerApplicationRepository;
    private final UserRepository userRepository;

    public List<OrganizerApplicationDto> getAllPending() {
        return organizerApplicationRepository.findAllByApprovedByIsNull()
                .stream()
                .map(OrganizerApplicationDto::new)
                .toList();
    }

    public void deny(UUID id) {
        organizerApplicationRepository.findById(id)
                .ifPresentOrElse(
                        organizerApplicationRepository::delete,
                        () -> {throw new BadRequestException("Application not found");}
                );
    }

    public void accept(UUID id, UUID organizerId) {
        OrganizerApplication application =  organizerApplicationRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Application not found"));

        if (userRepository.findByEmail(application.getEmail()).isPresent()) {
            // remove application with email already used
            organizerApplicationRepository.delete(application);
            throw new  BadRequestException("User already exists");
        }

        userRepository.save(new User()
                .setEmail(application.getEmail())
                .setPassword(application.getPassword()) // password is kept hashed
                .setUsername(application.getUsername())
                .setRole(UserRole.ORGANIZER));

        application.setApprovedBy(userRepository.findById(organizerId)
                .orElseThrow(() -> new BadRequestException("Approver not found.")));
        organizerApplicationRepository.save(application);

    }
}
