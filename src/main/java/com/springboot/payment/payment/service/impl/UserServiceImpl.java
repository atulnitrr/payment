package com.springboot.payment.payment.service.impl;

import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.payment.payment.dto.UserDto;
import com.springboot.payment.payment.entity.UserEntity;
import com.springboot.payment.payment.model.request.UserRequest;
import com.springboot.payment.payment.repository.UserRespository;
import com.springboot.payment.payment.service.UserService;


@Service
public class UserServiceImpl implements UserService  {

    private UserRespository userRespository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(final UserRespository userRespository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRespository = userRespository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override public UserDto createUser(final UserRequest userRequest) {
        final UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        userEntity.setUserId(UUID.randomUUID().toString());
        final UserEntity savedUser = userRespository.save(userEntity);
        final UserDto userDto = new UserDto();
        BeanUtils.copyProperties(savedUser, userDto);
        return userDto;
    }

    @Override public UserDto getUser(final String email) {
        final UserEntity userEntity = userRespository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        final UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final UserEntity userEntity = userRespository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), Collections.emptyList());
    }
}
