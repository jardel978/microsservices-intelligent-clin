package com.intelligentclin.users_service.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intelligentclin.users_service.model.dto.AuthRequest;
import com.intelligentclin.users_service.model.enums.TypeUser;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "auth_id")
    @GeneratedValue(generator = "users", strategy = GenerationType.SEQUENCE)
    private Long auth_id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(unique = true, nullable = false)
    @NotNull(message = "username is required")
    private String username;

    @Column(nullable = false)
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "The field 'role' is mandatory")
    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_users_roles", joinColumns = @JoinColumn(name = "auth_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private TypeUser type;

    public boolean isLoginCorrect(AuthRequest authRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(authRequest.password(), this.password);
    }

    public User(@NotNull User user) {
        this.auth_id = user.getAuth_id();
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.type = user.getType();
    }
   
}
