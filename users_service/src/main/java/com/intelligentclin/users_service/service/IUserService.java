package com.intelligentclin.users_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.intelligentclin.users_service.model.dto.AuthRequest;
import com.intelligentclin.users_service.model.dto.CreateUserDto;
import com.intelligentclin.users_service.model.dto.TokenResult;
import com.intelligentclin.users_service.model.entity.User;

public interface IUserService extends UserDetailsService {
   void newUser (CreateUserDto dto);
   TokenResult login (AuthRequest authRequest);
   TokenResult refreshToken(String refreshToken);
   Page<User> findAll (Pageable pageable);
}
