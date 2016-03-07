package com.ciandt.techgallery.service.transformer;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyLink;
import com.ciandt.techgallery.service.model.TechnologyLinkTO;

import java.util.ArrayList;
import java.util.List;

/**
 * TechnologyLinkConverter methods.
 *
 * @author Felipe Ibrahim
 *
 */
public class TechnologyLinkTransformer
    implements Transformer<TechnologyLink, TechnologyLinkTO> {

  /**
   * Transform entity from datastore into response entity which is transient.
   *
   * @param entity from datastore
   * @return transient entity
   */
  @Override
  public TechnologyLinkTO transformTo(TechnologyLink entity) {
    TechnologyLinkTO to = new TechnologyLinkTO();
    to.setId(entity.getId());
    to.setDescription(entity.getDescription());
    to.setLink(entity.getLink());
    to.setTechnologyId(entity.getTechnology().get().getId());
    to.setCreation(entity.getTimestamp());
    to.setAuthor(entity.getAuthor().get());
    return to;
  }

  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   *
   * @param entities entity list
   * @return list of transient entities
   */
  public List<TechnologyLinkTO> fromEntityToTransient(List<TechnologyLink> entities) {

    List<TechnologyLinkTO> to = new ArrayList<TechnologyLinkTO>();
    for (TechnologyLink entity : entities) {
      to.add(transformTo(entity));
    }

    return to;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   *
   * @param transient entity
   * @return entity from datastore
   */
  @Override
  public TechnologyLink transformFrom(TechnologyLinkTO tranzient) {
    TechnologyLink entity = new TechnologyLink();
    entity.setId(tranzient.getId());
    entity.setDescription(tranzient.getDescription());
    entity.setLink(tranzient.getLink());
    if (tranzient.getTechnologyId() != null) {
      Key<Technology> techKey = Key.create(Technology.class, tranzient.getTechnologyId());
      entity.setTechnology(Ref.create(techKey));
    } else {
      entity.setTechnology(null);
    }
    if (tranzient.getTechnologyId() != null) {
      Key<TechGalleryUser> authorKey =
          Key.create(TechGalleryUser.class, tranzient.getTechnologyId());
      entity.setAuthor(Ref.create(authorKey));
    } else {
      entity.setAuthor(null);
    }

    return entity;
  }

}
