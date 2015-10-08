package com.ciandt.techgallery.service.util;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;

import java.util.ArrayList;
import java.util.List;

/**
 * TechnologyCommentConverter methods.
 * 
 * @author Felipe Ibrahim
 *
 */
public class TechnologyCommentTransformer
    implements Transformer<TechnologyComment, TechnologyCommentTO> {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  @Override
  public TechnologyCommentTO transformTo(TechnologyComment entity) {
    TechnologyCommentTO commentTo = new TechnologyCommentTO();
    commentTo.setId(entity.getId());
    commentTo.setComment(entity.getComment());
    commentTo.setTechnologyId(entity.getTechnology().get().getId());
    commentTo.setCreation(entity.getTimestamp());
    commentTo.setAuthor(entity.getAuthor().get());

    return commentTo;
  }

  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   * 
   * @param entities entity list
   * @return list of transient entities
   */
  public List<TechnologyCommentTO> fromEntityToTransient(List<TechnologyComment> entities) {

    List<TechnologyCommentTO> commentsTo = new ArrayList<TechnologyCommentTO>();
    for (TechnologyComment entity : entities) {
      commentsTo.add(transformTo(entity));
    }

    return commentsTo;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   */
  @Override
  public TechnologyComment transformFrom(TechnologyCommentTO tranzient) {
    TechnologyComment commentEntity = new TechnologyComment();
    commentEntity.setId(tranzient.getId());
    commentEntity.setComment(tranzient.getComment());
    Key<Technology> techKey = Key.create(Technology.class, tranzient.getTechnologyId());
    commentEntity.setTechnology(Ref.create(techKey));
    Key<TechGalleryUser> authorKey = Key.create(TechGalleryUser.class, tranzient.getTechnologyId());
    Ref<TechGalleryUser> ref = Ref.create(authorKey);
    commentEntity.setAuthor(ref);
    return commentEntity;
  }
}
