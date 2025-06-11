package com.intelligentclin.users_service.model.dto;

public record TokenResult(String access_token, String refresh_token, Long expiresIn) {
}
