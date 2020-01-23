package com.exelatech.mrad.authfilter.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exelatech.mrad.authfilter.exceptions.AuthFilterException;
import com.exelatech.mrad.authfilter.model.SimpleAuthority;
import com.exelatech.mrad.authfilter.service.JWTUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * AuthFilter
 */
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

  @Autowired
  private JWTUtilService jwtUtilService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");

    String jws = null;
    String username = null;
    ArrayList<SimpleAuthority> authorities = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jws = authorizationHeader.substring(7);

      try {
        username = jwtUtilService.extractSubject(jws);
        authorities = jwtUtilService.extractAuthorities(jws);
      } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
          | IllegalArgumentException ex) {
        throw new AuthFilterException(ex.getMessage(), ex);
      }
    }
    
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(token);
    }

    filterChain.doFilter(request, response);
  }

}