package com.mobylab.springbackend.entity;

import com.mobylab.springbackend.enums.SubmissionStatus;
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
@Table(name = "race_submissions", schema = "project")
public class RaceSubmission extends BaseEntity {
    @JdbcTypeCode(Types.TIME)
    @Column(columnDefinition = "TIME(3)")
    private LocalTime time;

    @Column(name = "video_url")
    private String videoURL;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User driver;

    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;
}
