package com.ciant.techgallery.transaction.idempotency;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class EmptyIdempotencyHandler implements IdempotencyHandler {

  Object result;
  
  private static final Logger LOG = Logger.getLogger(AutoIdempotencyHandler.class.getName());
  
  @Override
  public boolean shouldTransactionProceed(Object proxy, Method method, Object[] args) {
    LOG.warning("Method " + method.getName() + " is not configured with autoIdempotency. "
        + "None idempotency check will be applied.");
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
