package com.zepto.zepto_backend.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{

  @Autowired
  JWTUtil jwtUtil;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException{
    String path = request.getRequestURI();

    if (path.startsWith("/api/v1/consumer/register") || path.startsWith("/api/v1/user/signin")) {
            filterChain.doFilter(request, response);
            return;
        }

      String authHeader = request.getHeader("Authorization");
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          filterChain.doFilter(request, response);
          return;
      }

      // 🔹 Extract token and validate
      String token = authHeader.substring(7);
      boolean isValid = jwtUtil.validateToken(token);

      if (!isValid) {
          filterChain.doFilter(request, response);
          return;
      }
      String payload = jwtUtil.decryptToken(token);
      UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(payload, null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    
    filterChain.doFilter(request, response);
  }
}
