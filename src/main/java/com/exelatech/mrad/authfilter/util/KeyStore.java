package com.exelatech.mrad.authfilter.util;

import java.security.Key;

/**
 * KeyStore
 */
public interface KeyStore {

  public Key getPublicKey();
  public Key getPrivateKey();
  
}