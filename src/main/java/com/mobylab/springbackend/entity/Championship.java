package com.mobylab.springbackend.entity;


import com.mobylab.springbackend.enums.ChampionshipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;


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
    private String racing_class;

    @Enumerated(EnumType.STRING)
    private ChampionshipStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
