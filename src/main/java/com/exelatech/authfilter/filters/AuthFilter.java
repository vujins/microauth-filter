package com.exelatech.authfilter.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exelatech.authfilter.model.SimpleAuthority;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * AuthFilter
 */
@Component
public class AuthFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");

    String jws = null;
    String username = null;
    ArrayList<SimpleAuthority> authorities = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jws = authorizationHeader.substring(7);
    }

  }

}