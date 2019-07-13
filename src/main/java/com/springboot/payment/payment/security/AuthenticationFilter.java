package com.springboot.payment.payment.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.google.gson.Gson;
import com.springboot.payment.payment.AppConsts;
import com.springboot.payment.payment.SpringApplicationContext;
import com.springboot.payment.payment.dto.UserDto;
import com.springboot.payment.payment.model.request.UserLoginRequest;
import com.springboot.payment.payment.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override public Authentication attemptAuthentication(final HttpServletRequest request,
            final HttpServletResponse response)
            throws AuthenticationException {
        final Gson gson = new Gson();
        try {
            final UserLoginRequest loginRequest = gson.fromJson(request.getReader(), UserLoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail()
                    , loginRequest.getPassword(), Collections.emptyList()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override protected void successfulAuthentication(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain, final Authentication authResult) throws IOException, ServletException {

        final String userName = ((User)authResult.getPrincipal()).getUsername();
        final String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)).signWith(SignatureAlgorithm.HS512,
                        AppConsts.SECRET_TOKEN).compact();

        response.addHeader(AppConsts.HEADER_PREFIX, AppConsts.TOKEN_PREFIX + token);
        final UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        final UserDto userDto = userService.getUser(userName);
        response.addHeader(AppConsts.USER_ID, userDto.getUserId());

    }
}
