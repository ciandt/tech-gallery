package com.ciandt.techgallery.service.util;

import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;

/**
 * TechnologyCommentConverter methods.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentConverter {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  public static TechnologyCommentTO fromEntityToTransient(TechnologyComment entity) {
    TechnologyCommentTO commentTO = new TechnologyCommentTO();

    commentTO.setId(entity.getId());
    commentTO.setComment(entity.getComment());
    commentTO.setTechnologyId(entity.getTechnology().get().getId());
    commentTO.setCreation(entity.getTimestamp());
    commentTO.setUser(entity.getAuthor().get());

    return commentTO;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   */
  public TechnologyComment fromTransientToEntity(TechnologyCommentTO tranzient) {
    // TODO Auto-generated method stub
    return null;
  }
}
