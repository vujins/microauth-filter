package com.exelatech.mrad.authfilter.service;

import java.security.Key;

/**
 * KeyStore
 */
public interface KeyStore {

  public Key getPublicKey();
  public Key getPrivateKey();
  
}