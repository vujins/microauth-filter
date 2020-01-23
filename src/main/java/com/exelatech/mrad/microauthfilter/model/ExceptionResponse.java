package com.exelatech.mrad.microauthfilter.model;

import lombok.Data;

/**
 * ExceptionResponse
 */
@Data
public class ExceptionResponse {

  private String timestamp;

  private Integer status;
  
  private String error;

  private String message;

  private String path;

  private String exception;

}