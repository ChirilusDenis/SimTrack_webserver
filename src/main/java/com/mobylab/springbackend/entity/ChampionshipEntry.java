package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "championship_entries",schema = "project")
public class ChampionshipEntry extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
}
