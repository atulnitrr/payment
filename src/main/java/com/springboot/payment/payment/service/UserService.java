package com.springboot.payment.payment.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.springboot.payment.payment.dto.UserDto;
import com.springboot.payment.payment.model.request.UserRequest;


public interface UserService extends UserDetailsService  {

    UserDto createUser(final UserRequest userRequest);
    UserDto getUser(final String email);
}
