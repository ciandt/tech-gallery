package com.ciandt.techgallery.service.transformer;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.config.Transformer;

public class TechnologyTransformer implements Transformer<Technology, TechnologyResponse> {

  @Override
  public Technology transformFrom(TechnologyResponse baseObject) {
    Technology product = new Technology();
    product.setAuthor(baseObject.getAuthor());
    product.setDescription(baseObject.getDescription());
    product.setId(baseObject.getId());
    product.setImage(baseObject.getImage());
    product.setName(baseObject.getName());
    product.setRecommendation(baseObject.getRecommendation());
    product.setShortDescription(baseObject.getShortDescription());
    product.setWebsite(baseObject.getWebsite());
    product.setPositiveRecomendationsCounter(baseObject.getPositiveRecomendationsCounter());
    product.setNegativeRecomendationsCounter(baseObject.getNegativeRecomendationsCounter());
    product.setCommentariesCounter(baseObject.getCommentariesCounter());
    product.setEndorsedsCounter(baseObject.getEndorsedsCounter());
    return product;
  }

  @Override
  public TechnologyResponse transformTo(Technology baseObject) {
    if (baseObject.getInactivatedDate() == null) {
      TechnologyResponse product = new TechnologyResponse();
      product.setAuthor(baseObject.getAuthor());
      product.setDescription(baseObject.getDescription());
      product.setId(baseObject.getId());
      product.setImage(baseObject.getImage());
      product.setName(baseObject.getName());
      product.setRecommendation(baseObject.getRecommendation());
      product.setShortDescription(baseObject.getShortDescription());
      product.setWebsite(baseObject.getWebsite());
      product.setPositiveRecomendationsCounter(baseObject.getPositiveRecomendationsCounter());
      product.setNegativeRecomendationsCounter(baseObject.getNegativeRecomendationsCounter());
      product.setCommentariesCounter(baseObject.getCommentariesCounter());
      product.setEndorsedsCounter(baseObject.getEndorsedsCounter());
      return product;
    } else {
      return null;
    }
  }

}
