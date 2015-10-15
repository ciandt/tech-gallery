package com.ciant.techgallery.transaction.idempotency;

import java.lang.reflect.Method;

public interface IdempotencyHandler {
  
  boolean shouldTransactionProceed(final Object proxy, Method method, final Object[] args);
  
  void setReturn(Object object);
  
  Object getReturn();
  
}
