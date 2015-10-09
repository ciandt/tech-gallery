package com.ciant.techgallery.transaction.idempotency;

import com.ciant.techgallery.transaction.Transactional;

public class IdempotencyHandlerFactory {

  public static IdempotencyHandler createHandlerAnnotationBased(Transactional transactional){
    
    if(transactional.autoIdempotency()){
      return new AutoIdempotencyHandler();
    } else if(transactional.keyIdempotency()){
      return new KeyIdempotencyHandler();
    }
    
    return new EmptyIdempotencyHandler();
  }
  
}
