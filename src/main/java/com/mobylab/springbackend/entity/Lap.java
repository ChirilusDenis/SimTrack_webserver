package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laps", schema = "project")
public class Lap extends BaseEntity {
    @JdbcTypeCode(Types.TIME)
    @Column(columnDefinition = "TIME(3)")
    private LocalTime time;
    private int points;

    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @ManyToOne
    @JoinColumn(name = "verified_by_id", nullable = false)
    private User verifiedBy;
}
