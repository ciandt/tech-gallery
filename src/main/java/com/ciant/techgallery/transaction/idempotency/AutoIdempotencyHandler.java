package com.ciant.techgallery.transaction.idempotency;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyRange;

import com.googlecode.objectify.impl.EntityMetadata;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class AutoIdempotencyHandler implements IdempotencyHandler {

  private Key generatedKey;
  
  private Object result;
  
  private static final Logger LOG = Logger.getLogger(AutoIdempotencyHandler.class.getName());
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean shouldTransactionProceed(Object target, Method method, Object[] args) {
    
    for (Object arg : args) {
      
      try {
        EntityMetadata<Object> metadata = 
            (EntityMetadata<Object>) ofy().factory().getMetadata(arg.getClass());
        DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
        
        try {
          Key key = metadata.getKeyMetadata().getRawKey(arg);
          if (key != null && key.isComplete()) {
            generatedKey = key;
          }
        } catch (IllegalArgumentException iae) {
          //do nothing. Will be handled after
        }
        
        //havent generated key. First time executing
        if (generatedKey == null && metadata.getKeyMetadata().isIdGeneratable()) {
    
          KeyRange keyRange = dataStoreService.allocateIds(metadata.getKeyMetadata().getKind(), 1);
          generatedKey = keyRange.getStart();
      
        } else {
          try {
            dataStoreService.get(generatedKey);
            return false;
          } catch (EntityNotFoundException e) {
            //do nothing. Should continue
          }
        }
        if (metadata.getKeyMetadata().isIdGeneratable()) {
          metadata.getKeyMetadata().setLongId(args[0], generatedKey.getId());
        }
        return true;
      } catch (IllegalArgumentException iae) {
        continue;
      }
    }
    
    LOG.warning("Method " + method 
        + " using autoIdempotency but no entity was passed in the parameters. "
        + "No idempotency check will be applied.");
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
