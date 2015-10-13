package com.ciant.techgallery.transaction.idempotency;

import com.ciant.techgallery.transaction.Transactional;

public class IdempotencyHandlerFactory {

  /**
   * @param transactional annotation written in a transactional method.
   * @return Creates an IdempotencyHandler based on Transactional annotation.
   */
  public static IdempotencyHandler createHandlerAnnotationBased(Transactional transactional) {
    
    if (transactional.autoIdempotency()) {
      return new AutoIdempotencyHandler();
    } else if (transactional.keyIdempotency()) {
      return new KeyIdempotencyHandler();
    }
    
    return new EmptyIdempotencyHandler();
  }
}
