package com.intelligentclin.users_service.model.dto;

public record CreateUserDto(
    String username,
    String password,
    String name,
    String cpf) {}