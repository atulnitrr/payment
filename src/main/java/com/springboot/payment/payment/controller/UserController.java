package com.springboot.payment.payment.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.payment.payment.dto.UserDto;
import com.springboot.payment.payment.model.request.AddressRequest;
import com.springboot.payment.payment.model.request.UserRequest;
import com.springboot.payment.payment.model.response.UserResponse;
import com.springboot.payment.payment.service.UserService;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUser() {

        final List<UserDto> userDtoList = userService.getAllUsers();
        final List<UserResponse> userResponseList = new ArrayList<>();

        for (final UserDto userDto : userDtoList) {
            final UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(userDto, userResponse);
            userResponseList.add(userResponse);
        }

        return ResponseEntity.ok().body(userResponseList);
    }


    @PostMapping()
    private ResponseEntity<UserResponse> createUser(@RequestBody final UserRequest userRequest) {
        final UserResponse userResponsen = new UserResponse();
        final UserDto userDto = userService.createUser(userRequest);
        BeanUtils.copyProperties(userDto, userResponsen);
        return ResponseEntity.ok().body(userResponsen);
    }
}
