package com.ciandt.techgallery.service.util;

import java.util.ArrayList;
import java.util.List;

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
    commentTO.setAuthor(entity.getAuthor().get());

    return commentTO;
  }
  
  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   * 
   * @param list entity from datastore
   * @return list transient entity
   */
  public static List<TechnologyCommentTO> fromEntityToTransient(List<TechnologyComment> entities) {
    
    List<TechnologyCommentTO> commentsTO = new ArrayList<TechnologyCommentTO>();
    for (TechnologyComment entity : entities) {
      commentsTO.add(fromEntityToTransient(entity));
    }

    return commentsTO;
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
