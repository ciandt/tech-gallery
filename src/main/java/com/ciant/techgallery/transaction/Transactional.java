package com.ciant.techgallery.transaction;

import com.googlecode.objectify.TxnType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Transactional {

  /**
   * Transaction type.
   */
  TxnType type() default TxnType.REQUIRED;
  
  /**
   * If Idempotency will be automatically handled by Key.
   * Key must be one of the parameters. 
   */
  boolean keyIdempotency() default false;
  
  /**
   * If Idempotency will be automatically handled.
   * \@Entity must be the first parameter and its id 
   * must be Long. 
   */
  boolean autoIdempotency() default false;
  
}
