package com.intelligentclin.users_service.model.dto;

import java.util.Set;

import com.intelligentclin.users_service.model.entity.Role;

public record CreateUserDto(
    String username,
    String password,
    String name,
    String cpf) {}