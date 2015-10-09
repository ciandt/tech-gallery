package com.ciant.techgallery.transaction.idempotency;

public class EmptyIdempotencyHandler implements IdempotencyHandler {

  Object result;
  
  @Override
  public boolean shouldTransactionProceed(Object proxy, Object[] args) {
    return true;
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
