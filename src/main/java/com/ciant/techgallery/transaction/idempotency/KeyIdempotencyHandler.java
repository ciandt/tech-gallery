package com.ciant.techgallery.transaction.idempotency;

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class KeyIdempotencyHandler implements IdempotencyHandler {

  private Object result;

  @Override
  public boolean shouldTransactionProceed(Object target, Object[] args) {
    
    if (args != null) {
      com.googlecode.objectify.Key<?> key;
      for (Object arg : args) {
        
        if (arg instanceof com.googlecode.objectify.Key<?>) {
          key = (Key<?>) arg;
          Object result = ofy().load().key(key).now();
          return result==null;
        }
      }
    }
    
    return false;
  }

  @Override
  public void setReturn(Object object) {
    this.result = object;
  }

  @Override
  public Object getReturn() {
    return this.result;
  }
}
