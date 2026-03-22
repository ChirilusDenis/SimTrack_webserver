package com.mobylab.springbackend.entity;

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
@Table(name = "laps", schema = "project")
public class Lap extends BaseEntity {
    private long time;
    private int points;

    @OneToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @OneToOne
    @JoinColumn(name = "verified_by_id")
    private User verifiedBy;
}
