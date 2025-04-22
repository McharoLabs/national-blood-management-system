package com.nbtsms.identity_service.config;

import com.nbtsms.identity_service.exception.ForbiddenAccessException;
import com.nbtsms.identity_service.exception.UnauthorizedAccessException;
import com.nbtsms.identity_service.service.impl.JwtServiceImpl;
import com.nbtsms.identity_service.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtService;
    private final UserDetailsServiceImpl userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Value("${auth.endpoint}")
    private String authEndpoint;


    public JwtAuthenticationFilter(JwtServiceImpl jwtService, UserDetailsServiceImpl userService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/api/v1/swagger-ui",
            "/api/v1/swagger-ui/",
            "/api/v1/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/api-docs",
            "/api-docs/**"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            String servletPath = request.getServletPath();

            for (String path : PUBLIC_PATHS) {
                if (servletPath.startsWith(path)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            if (servletPath.startsWith(authEndpoint)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (StringUtils.isEmpty(authHeader)) {
                throw new UnauthorizedAccessException("Authorization header is missing. Please provide a valid token.");
            }

            if (!authHeader.startsWith("Bearer ")) {
                throw new ForbiddenAccessException("Invalid Authorization header format. Expected 'Bearer <token>'.");
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail);

                if (jwtService.isTokeValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

}