package com.exelatech.authfilter.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exelatech.authfilter.exceptions.AuthFilterException;
import com.exelatech.authfilter.model.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ExceptionHandlerFilter
 */
public class RestExceptionHandlerFilter extends OncePerRequestFilter {

  @Autowired
	private ObjectMapper jackson;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (AuthFilterException ex) {
      ExceptionResponse body = new ExceptionResponse();
      HttpStatus status = HttpStatus.FORBIDDEN;

      body.setStatus(status.value());
      body.setError("Forbidden | Custom Error");
      body.setMessage(ex.getMessage());
      body.setPath(request.getRequestURI());
      body.setTimestamp(new Date().toString());

      response.setStatus(status.value());
      response.setContentType("application/json");
      response.getWriter().write(jackson.writeValueAsString(body));
      return;
    }

  }

}