package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.SubmissionStatus;
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
@Table(name = "race_submissions", schema = "project")
public class RaceSubmission extends BaseEntity {
    private long time;
    private String videoURL;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User driver;

    @OneToOne
    @JoinColumn(name = "race_id")
    private Race race;
}
