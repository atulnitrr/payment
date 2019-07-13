package com.springboot.payment.payment.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override protected void configure(final HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll()
                .anyRequest().authenticated();


        // dont commit this, this is to see h2-console log
        http.headers().frameOptions().disable();
    }


    @Override protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
