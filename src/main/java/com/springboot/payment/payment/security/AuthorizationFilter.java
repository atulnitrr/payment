package com.springboot.payment.payment.security;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.springboot.payment.payment.AppConsts;
import io.jsonwebtoken.Jwts;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public AuthorizationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        final String authHeader = request.getHeader(AppConsts.HEADER_PREFIX);
        if (authHeader == null || !authHeader.startsWith(AppConsts.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;

        }

        final UsernamePasswordAuthenticationToken authenticationToken = getToken(authHeader);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getToken(final String tokenValue) {

        if (tokenValue != null) {
            final String token = tokenValue.replace(AppConsts.TOKEN_PREFIX, "");
            final String user = Jwts.parser()
                    .setSigningKey(AppConsts.SECRET_TOKEN)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
}
