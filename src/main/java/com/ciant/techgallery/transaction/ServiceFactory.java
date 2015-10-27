package com.ciant.techgallery.transaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates Services intances. All services must be singletons and can be or not Transactional
 * 
 * @author bcarneiro
 *
 */
public class ServiceFactory {

  private static Map<String, Object> cache = new HashMap<String, Object>();

  /**
   * Creates a service instance following the rules below. -If a service implementation has
   * Transactional annotation should be returned a new Transactional Proxy for the implementation
   * -Else create a plain instance of implementation class
   * 
   * @param interfac return type
   * @param implementation what implementation will be
   * @return new Service based on the rules above
   */
  @SuppressWarnings("unchecked")
  public static <T> T createServiceImplementation(Class<T> interfac, Class<?> implementation) {

    String key = implementation.getName();

    if (cache.containsKey(key)) {
      return (T) cache.get(key);
    } else if (implementation.getAnnotation(Transactional.class) != null) {
      T proxy = (T) Proxy.newProxyInstance(interfac.getClassLoader(), new Class[] {interfac},
          new TransactionalInvocationHandler(implementation));
      cache.put(key, proxy);
      return proxy;
    } else {
      try {
        Constructor<T> constructor = (Constructor<T>) implementation.getDeclaredConstructor();
        constructor.setAccessible(true);
        T newInstance = (T) constructor.newInstance();
        cache.put(key, newInstance);
        return newInstance;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

}
