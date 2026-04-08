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

    public List<OrganizerApplicationDto> getAll() {
        return organizerApplicationRepository.findAll()
                .stream()
                .map(OrganizerApplicationDto::new)
                .toList();
    }

    public void deny(UUID id) {
        organizerApplicationRepository.findById(id)
                .ifPresentOrElse(
                        (app) -> organizerApplicationRepository.delete(app),
                        () -> new BadRequestException("Application not found"));
    }

    public void accept(UUID id) {
        OrganizerApplication application =  organizerApplicationRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Application not found"));

        userRepository.save(new User()
                .setEmail(application.getEmail())
                .setPassword(application.getPassword()) // password is kept hashed
                .setUsername(application.getUsername())
                .setRole(UserRole.ORGANIZER));

        organizerApplicationRepository.delete(application);

    }
}
