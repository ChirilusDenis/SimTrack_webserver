package com.mobylab.springbackend.entity;


import com.mobylab.springbackend.enums.ChampionshipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "championships", schema = "project")
public class Championship extends BaseEntity {
    private String name;
    private String description;

    @Column(name = "racing_class")
    private String racingClass;

    @Enumerated(EnumType.STRING)
    private ChampionshipStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "championship")
    private List<ChampionshipEntry> championshipEntries;

    @OneToMany(mappedBy = "championship")
    private List<Race> championshipRaces;
}
