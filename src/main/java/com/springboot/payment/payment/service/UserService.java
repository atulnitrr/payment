package com.springboot.payment.payment.service;

import com.springboot.payment.payment.dto.UserDto;
import com.springboot.payment.payment.model.request.UserRequest;


public interface UserService {

    UserDto createUser(final UserRequest userRequest);
}
