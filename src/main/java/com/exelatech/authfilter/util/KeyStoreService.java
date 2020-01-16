package com.exelatech.authfilter.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * KeyStore
 */
@Service
public class KeyStoreService {

  private PublicKey key = null;
  private final String algorithm = "RSA";

  @Autowired
  private AuthService authService;

  private PublicKey getKeyFromAuthServer() {

    byte[] bytes = authService.getKey();
    PublicKey publicKey = null;

    try {
      publicKey = KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(bytes));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return publicKey;
  }

  public PublicKey getKey() {
    
    if (key != null) {
      return key;
    } else {
      return getKeyFromAuthServer();
    }
  }

}