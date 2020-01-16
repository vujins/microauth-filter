package com.exelatech.authfilter.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class SimpleAuthority implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  private String authority;

  public SimpleAuthority(String authority) {
    this.authority = authority;
  }

  public SimpleAuthority() {
  }

}