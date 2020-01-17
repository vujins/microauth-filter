package com.exelatech.mrad.authfilter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KeyResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyResponse {

  private byte[] key;
  
}