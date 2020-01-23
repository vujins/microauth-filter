package com.exelatech.mrad.microauthfilter.service;

import java.security.Key;

/**
 * KeyStore
 */
public interface KeyStore {

  public Key getPublicKey();
  public Key getPrivateKey();
  
}