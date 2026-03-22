package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.ApplicationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    private ApplicationStatus applicationStatus;
}
