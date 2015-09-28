package com.ciandt.techgallery.service.util;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TechnologyConverter methods.
 * 
 * @author Thulio Ribeiro
 *
 */
public class TechnologyConverter {

  /**
   * Transform entity from datastore into response entity which is transient.
   * 
   * @param entity from datastore
   * @return transient entity
   */
  public static TechnologyResponse fromEntityToTransient(Technology entity) {
    TechnologyResponse technologyResponse = new TechnologyResponse();
    
    technologyResponse.setId(entity.getId());
    technologyResponse.setName(entity.getName());
    technologyResponse.setShortDescription(entity.getShortDescription());
    technologyResponse.setAuthor(entity.getAuthor());
    technologyResponse.setDescription(entity.getDescription());
    technologyResponse.setImage(entity.getImage());
    technologyResponse.setWebsite(entity.getWebsite());
    technologyResponse.setRecommendation(entity.getRecommendation());

    return technologyResponse;
  }

  /**
   * Transform a list of entity from datastore into list of response entity which is transient.
   * 
   * @param list entity from datastore
   * @return list transient entity
   */
  public static List<TechnologyResponse> fromEntityToTransient(List<Technology> entities) {

    List<TechnologyResponse> technologiesResponse = new ArrayList<TechnologyResponse>();
    for (Technology entity : entities) {
      technologiesResponse.add(fromEntityToTransient(entity));
    }

    return technologiesResponse;
  }

  /**
   * Transform entity from response which is transient into datastore entity which can be persisted.
   * 
   * @param transient entity
   * @return entity from datastore
   */
  public static Technology fromTransientToEntity(TechnologyResponse transientObject) {
    Technology entity = new Technology();
    entity.setId(transientObject.getId());
    entity.setName(transientObject.getName());
    entity.setShortDescription(transientObject.getShortDescription());
    entity.setDescription(transientObject.getDescription());
    entity.setAuthor(transientObject.getAuthor());
    entity.setWebsite(transientObject.getWebsite());
    entity.setImage(transientObject.getImage());
    entity.setRecommendation(transientObject.getRecommendation());
    return entity;
  }
}
