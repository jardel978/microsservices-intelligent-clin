package com.intelligentclin.users_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.intelligentclin.users_service.model.entity.Role;
import com.intelligentclin.users_service.model.entity.User;
import com.intelligentclin.users_service.repository.IRoleRepository;
import com.intelligentclin.users_service.repository.IUserRepository;

import java.util.Set;
import java.util.UUID;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByNameIgnoreCase(Role.Values.ADMIN.name());
        
        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin alwready exists");
                },
                () -> {
                    if (roleAdmin != null) {
                        System.out.println("role admin:" + roleAdmin.getName());
                        var user = new User();
                        user.setUid(UUID.randomUUID().toString());
                        user.setUsername("admin");
                        user.setPassword(passwordEncoder.encode("12345678"));
                        user.setRoles(Set.of(roleAdmin));
                        userRepository.save(user);
                    }
                });
    }
}