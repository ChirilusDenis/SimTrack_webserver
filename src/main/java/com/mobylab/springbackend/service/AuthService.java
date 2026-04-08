package com.mobylab.springbackend.service;

import com.mobylab.springbackend.config.security.JwtGenerator;
import com.mobylab.springbackend.entity.OrganizerApplication;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.enums.UserRole;
import com.mobylab.springbackend.exception.BadRequestException;
import com.mobylab.springbackend.repository.OrganizerApplicationRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.LoginDto;
import com.mobylab.springbackend.service.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private OrganizerApplicationRepository organizerApplicationRepository;

    @Autowired
    private JwtGenerator jwtGenerator;


    public String register(RegisterDto registerDto) {

        if (userRepository.existsUserByEmail(registerDto.getEmail())) {
            throw new BadRequestException("Email is already used");
        }

        if (registerDto.getRole() == UserRole.ORGANIZER) {
            //create organizer creation request
            organizerApplicationRepository.save(new OrganizerApplication()
                    // password is kept hashed
                    .setPassword(passwordEncoder.encode(registerDto.getPassword()))
                    .setEmail(registerDto.getEmail())
                    .setUsername(registerDto.getUsername()));

            return "Organizer applications has been registered successfully";
        } else {
            userRepository.save(new User()
                    .setEmail(registerDto.getEmail())
                    .setPassword(passwordEncoder.encode(registerDto.getPassword()))
                    .setUsername(registerDto.getUsername())
                    .setRole(registerDto.getRole()));
            return "User registered successfully";
        }
    }

    public String login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findUserByEmail(loginDto.getEmail());
        if(optionalUser.isEmpty()) {
            throw new BadRequestException("Wrong credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);

    }
}
