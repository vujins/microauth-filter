package com.exelatech.mrad.authfilter.util;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * KeyStore
 */
public interface KeyStore {

  public PublicKey getPublicKey();
  public PrivateKey getPrivateKey();
  
}