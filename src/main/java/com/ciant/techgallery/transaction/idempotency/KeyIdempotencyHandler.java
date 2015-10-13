package com.ciant.techgallery.transaction.idempotency;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.lang.reflect.Method;

public class KeyIdempotencyHandler implements IdempotencyHandler {

  private Object result;

  @Override
  public boolean shouldTransactionProceed(Object target, Method method, Object[] args) {
    
    if (args != null) {
      Key<?> key;
      for (Object arg : args) {
        
        if (arg instanceof Key<?>) {
          key = (Key<?>) arg;
          Object result = ObjectifyService.ofy().load().key(key).now();
          return result == null;
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
