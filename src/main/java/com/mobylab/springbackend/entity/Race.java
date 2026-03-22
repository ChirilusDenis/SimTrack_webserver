package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.ChampionshipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "races", schema = "project")
public class Race extends BaseEntity {
    private String name;

    @Column(name = "track_name")
    private String trackName;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @OneToMany(mappedBy = "race")
    private List<Lap> raceLaps;

    @OneToMany(mappedBy = "race")
    private List<RaceSubmission> raceSubmissions;

}
