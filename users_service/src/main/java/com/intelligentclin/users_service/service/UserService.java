package com.intelligentclin.users_service.service;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelligentclin.common_models.models.dtos.attendat.AttendantReq;
import com.intelligentclin.common_models.models.dtos.dentist.DentistReq;
import com.intelligentclin.users_service.model.dto.AuthRequest;
import com.intelligentclin.users_service.model.dto.CreateUserDto;
import com.intelligentclin.users_service.model.dto.TokenResult;
import com.intelligentclin.users_service.model.entity.Role;
import com.intelligentclin.users_service.model.entity.User;
import com.intelligentclin.users_service.model.enums.TypeUser;
import com.intelligentclin.users_service.repository.IRoleRepository;
import com.intelligentclin.users_service.repository.IUserRepository;
import com.intelligentclin.users_service.service.feign.IClinicFeignClient;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IClinicFeignClient clinicFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found with id "));
        return new CustomUserDetails(user);
    }

    @Override
    public void newUser(CreateUserDto dto) {
        
        var userFromDb = userRepository.findByUsername(dto.getUsername());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        var user = new User();
        String userUid = UUID.randomUUID().toString();
        user.setUid(userUid);
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        if (dto.getType() == null) {
            user.setType(TypeUser.ATTENDANT);
        } else {
            user.setType(dto.getType());
        }
        switch (user.getType()) {
            case ATTENDANT:
                clinicFeignClient.createAttendant(new AttendantReq(userUid, dto.getFirstName(), dto.getFirstName(), dto.getCpf(), dto.getEmail(), dto.getPhone(), dto.getGender()));
                user.setRoles(Set.of(roleRepository.findByNameIgnoreCase(Role.Values.ATTENDANT.name())));
                break;
            case DENTIST:
                clinicFeignClient.createDentist(new DentistReq(userUid, dto.getFirstName(), dto.getFirstName(), dto.getCpf(), dto.getEmail(), dto.getPhone(), dto.getRegistrationNumber(), dto.getSpecialties()));
                user.setRoles(Set.of(roleRepository.findByNameIgnoreCase(Role.Values.DENTIST.name())));
                break;        
            default:
                break;
        }
        userRepository.save(user);
    }

    @Override
    public TokenResult login(AuthRequest authRequest) {
        var user = userRepository.findByUsername(authRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(authRequest, passwordEncoder)) {
            throw new BadCredentialsException("invalid credentials.");
        }

        var now = Instant.now();
        var accessExpiresIn = 60 * 60L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("intelligentclin")
                .subject(user.get().getAuth_id().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessExpiresIn))
                .claim("scope", scopes)
                .build();
        var accessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        var refreshExpiresIn = 60 * 60 * 24 * 2L; // 2 days
        var refreshClaims = JwtClaimsSet.builder()
                .issuer("intelligentclin")
                .subject(user.get().getAuth_id().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(refreshExpiresIn))
                .claim("type", "refresh")
                .build();

        var refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();
        return new TokenResult(accessToken, refreshToken, accessExpiresIn);
    }

    @Override
    public TokenResult refreshToken(String refreshToken) {
        Jwt decodedJwt;
        try {
            decodedJwt = jwtDecoder.decode(refreshToken);
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        if (!"refresh".equals(decodedJwt.getClaimAsString("type"))) {
            throw new BadCredentialsException("Invalid token type");
        }

        var userId = decodedJwt.getSubject();
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var now = Instant.now();

        var accessExpiresIn = 60 * 5L; // 5 minutos
        var scopes = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var accessClaims = JwtClaimsSet.builder()
                .issuer("intelligentclin")
                .subject(user.getAuth_id().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessExpiresIn))
                .claim("scope", scopes)
                .build();
        var accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();

        return new TokenResult(accessToken, refreshToken, accessExpiresIn);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    private static final class CustomUserDetails extends User implements UserDetails {
        CustomUserDetails(@NotNull User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("SCOPE_" + role.getName().toUpperCase()))
            .collect(Collectors.toSet());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
        
}
