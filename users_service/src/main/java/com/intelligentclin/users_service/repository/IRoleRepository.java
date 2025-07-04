package com.intelligentclin.users_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intelligentclin.users_service.model.entity.Role;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByNameIgnoreCase(String name);
}