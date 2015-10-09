package com.ciant.techgallery.transaction.idempotency;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyRange;
import com.googlecode.objectify.impl.EntityMetadata;

public class AutoIdempotencyHandler implements IdempotencyHandler {

  private Key generatedKey;
  
  private Object result;
  
  @Override
  public boolean shouldTransactionProceed(Object target, Object[] args) {

    EntityMetadata<Object> metadata = 
        (EntityMetadata<Object>) ofy().factory().getMetadata(args[args.length - 1].getClass());
    
    DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
    
    //havent generated key. First time executing
    if (generatedKey == null) {

      KeyRange keyRange = dataStoreService.allocateIds(metadata.getKeyMetadata().getKind(), 1);
      generatedKey = keyRange.getStart();
  
    } else {
      try {
        dataStoreService.get(generatedKey);
        return true;
      } catch (EntityNotFoundException e) {
        //do nothing. Should continue
      }
    }
    
    metadata.getKeyMetadata().setLongId(args[0], generatedKey.getId());
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
