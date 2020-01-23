
package com.exelatech.mrad.microauthfilter.service;

import com.exelatech.mrad.microauthfilter.model.KeyResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * AuthService
 */
@Service
public class AuthService {

  private final String url = "http://localhost:9091/publickey";

  @Autowired
  private RestTemplateBuilder builder;

  public byte[] getKey() {

    RestTemplate restTemplate = builder.build();

    KeyResponse response = null;

    try {
      response = restTemplate.getForObject(url, KeyResponse.class);
      return response.getKey();
    } catch (RestClientException ex) {
      ex.printStackTrace();
    }

    return null;
  }

}