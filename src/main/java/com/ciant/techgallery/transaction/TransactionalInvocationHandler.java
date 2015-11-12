package com.ciant.techgallery.transaction;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

import com.ciant.techgallery.transaction.idempotency.IdempotencyHandler;
import com.ciant.techgallery.transaction.idempotency.IdempotencyHandlerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Transactional interceptor. Checks if the method which is being called should run in a transaction
 * or not.
 * 
 * @author bcarneiro
 *
 */
public class TransactionalInvocationHandler implements InvocationHandler {

  private static Logger log = Logger.getLogger(TransactionalInvocationHandler.class.getName());
  private Object implementation;

  /**
   * @param implementation - Instance that will be wrapped and called in a transaction or not
   *        depending Transactional annotation.
   */
  public TransactionalInvocationHandler(Class<?> implementation) {
    try {
      Constructor<?> constructor = implementation.getDeclaredConstructor();
      constructor.setAccessible(true);
      this.implementation = constructor.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] args)
      throws Throwable {
    try {
      final Method implementationMethod =
          implementation.getClass().getMethod(method.getName(), method.getParameterTypes());
      final Transactional transactional = implementationMethod.getAnnotation(Transactional.class);

      if (transactional != null) {

        final IdempotencyHandler idempotencyHandler =
            IdempotencyHandlerFactory.createHandlerAnnotationBased(transactional);

        return ObjectifyService.ofy().execute(transactional.type(), new Work() {
          @Override
          public Object run() {
            if (idempotencyHandler.shouldTransactionProceed(proxy, method, args)) {
              Object result = invokeMethod(args, implementationMethod);
              idempotencyHandler.setReturn(result);
            }
            return idempotencyHandler.getReturn();
          }
        });

      } else {
        return invokeMethod(args, implementationMethod);
      }
    } catch (RuntimeException err) {
      throw getRootCause(err, 5);
    }
  }

  private Throwable getRootCause(Throwable err, int level) {
    return level > 0 && err.getCause() != null ? getRootCause(err.getCause(), level - 1) : err;
  }

  private Object invokeMethod(final Object[] args, final Method implementationMethod) {
    try {
      return implementationMethod.invoke(implementation, args);
    } catch (Exception e) {
      log.log(Level.SEVERE, "Error in a transactional method", e);
      if (e.getCause() != null) {
        e = (Exception) e.getCause();
      }

      if (e instanceof RuntimeException) {
        throw (RuntimeException) e;
      }

      throw new RuntimeException(e);
    }
  }
}