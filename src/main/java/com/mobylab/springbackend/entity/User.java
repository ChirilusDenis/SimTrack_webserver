package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import com.mobylab.springbackend.enums.UserRole;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "project")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
