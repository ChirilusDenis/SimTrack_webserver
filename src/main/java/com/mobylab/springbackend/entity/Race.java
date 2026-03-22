package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.ChampionshipStatus;
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
@Table(name = "races", schema = "project")
public class Race extends BaseEntity {
    private String name;
    private String trackname;

    @OneToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

}
