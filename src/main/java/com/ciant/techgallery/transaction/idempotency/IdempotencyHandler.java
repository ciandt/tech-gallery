package com.ciant.techgallery.transaction.idempotency;

public interface IdempotencyHandler {
  
  boolean shouldTransactionProceed(final Object proxy, final Object[] args);
  
  void setReturn(Object object);
  
  Object getReturn();
  
}
