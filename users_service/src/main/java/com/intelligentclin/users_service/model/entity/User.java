package com.intelligentclin.users_service.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intelligentclin.users_service.model.dto.AuthRequest;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
@SequenceGenerator(name = "users", sequenceName = "SQ_TB_USERS", allocationSize = 1)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "auth_id")
    @GeneratedValue(generator = "users", strategy = GenerationType.UUID)
    private UUID auth_id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(unique = true, nullable = false)
    @NotNull(message = "username is required")
    private String username;

    @Column(nullable = false)
    @ToString.Exclude
    @Size(min = 6, max = 15, message = "password must be longer than 6 characters.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "The field 'role' is mandatory")
    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public boolean isLoginCorrect(AuthRequest authRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(authRequest.password(), this.password);
    }

    public User(@NotNull User user) {
        this.auth_id = user.getAuth_id();
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }
   
}
