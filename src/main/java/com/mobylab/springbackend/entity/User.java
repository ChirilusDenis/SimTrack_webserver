package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import com.mobylab.springbackend.enums.UserRole;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "project")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "createdBy")
    private List<Championship> championships;

    @OneToMany(mappedBy = "user")
    private List<ChampionshipEntry> championshipEntries;

    @OneToMany(mappedBy = "verifiedBy")
    private List<Lap> verifiedLaps;

    @OneToMany(mappedBy = "driver")
    private List<Lap> drivenLaps;

    @OneToMany(mappedBy = "driver")
    private List<RaceSubmission>  raceSubmissions;
}
