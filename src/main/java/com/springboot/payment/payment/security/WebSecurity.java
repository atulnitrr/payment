package com.springboot.payment.payment.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.springboot.payment.payment.AppConsts;
import com.springboot.payment.payment.service.UserService;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @Autowired
    public WebSecurity(final BCryptPasswordEncoder bCryptPasswordEncoder,
            final UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }


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
                .antMatchers(HttpMethod.GET, "/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        // dont commit this, this is to see h2-console log
        http.headers().frameOptions().disable();
    }


    @Override protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

    }

    private AuthenticationFilter getAuthFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/users/login");
        return authenticationFilter;
    }

    /**
     * Expose headers
     * @return
     */
    @Bean CorsConfigurationSource corsConfiguration() {

        final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setExposedHeaders(Arrays.asList(AppConsts.HEADER_PREFIX, AppConsts.USER_ID));
        corsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return corsConfigurationSource;

    }
}
