package com.exelatech.mrad.microauthfilter.service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * KeyStore
 */
@Service
public class KeyStoreService implements KeyStore {

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
      e.printStackTrace();
    }

    return publicKey;
  }

  @Override
  public PublicKey getPublicKey() {

    if (key != null) {
      return key;
    } else {
      return key = getKeyFromAuthServer();
    }
  }

  @Override
  public PrivateKey getPrivateKey() {
    return null;
  }
}