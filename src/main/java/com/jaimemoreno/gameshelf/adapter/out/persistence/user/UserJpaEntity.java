package com.jaimemoreno.gameshelf.adapter.out.persistence.user;

import com.jaimemoreno.gameshelf.domain.user.Role;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 32)
    private Set<Role> roles;

    @Column(nullable = false)
    private boolean enabled;

    UserJpaEntity(Long id, String username, String password, Set<Role> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }
}
