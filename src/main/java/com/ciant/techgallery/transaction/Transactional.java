package com.ciant.techgallery.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.objectify.TxnType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Transactional {

  /**
   * Transaction type.
   */
  TxnType type() default TxnType.REQUIRED;
  
  boolean keyIdempotency() default false;
  
  boolean autoIdempotency() default false;
  
}
