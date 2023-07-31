package com.crud.project.security.jwt;

import com.crud.project.security.services.UserDetailsServiceImpl;
import com.crud.project.service.SessionService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (StringUtils.startsWith(requestTokenHeader, "Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                if (sessionService.findByAuthTokenExist(jwtToken)) {
                    String username = jwtUtils.getUsernameFromToken(jwtToken);
                    if (StringUtils.isNotEmpty(username)
                            && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        if (jwtUtils.validateToken(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext()
                                    .setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                } else {
                    logger.error("Session not found in database");
                }
            } catch (IllegalArgumentException e) {
                logger.error("Unable to fetch JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token is expired");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
