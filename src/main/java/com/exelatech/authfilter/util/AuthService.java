
package com.exelatech.authfilter.util;

import com.exelatech.authfilter.model.KeyResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AuthService
 */
@Service
public class AuthService {

  private final String url = "http://localhost:8080/authenticate/key";

  @Autowired
  private RestTemplateBuilder builder;

  public byte[] getKey() {

    RestTemplate restTemplate = builder.build();

    KeyResponse response = restTemplate.getForObject(url, KeyResponse.class);

    return response.getKey();
  }
  
}