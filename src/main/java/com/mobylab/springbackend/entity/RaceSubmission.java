package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "race_submissions", schema = "project")
public class RaceSubmission extends BaseEntity {
    private LocalTime time;

    @Column(name = "video_url")
    private String videoURL;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
}
