package com.ciandt.techgallery.service.util;

import com.google.api.server.spi.config.Transformer;

import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public class TechnologyRecommendationTransformer
    implements Transformer<TechnologyRecommendation, TechnologyRecommendationTO> {

  private TechnologyCommentConverter commentConverter = new TechnologyCommentConverter();
  private TechnologyTransformer techTransformer = new TechnologyTransformer();
  private TechGalleryUserTransformer tgUserTransformer = new TechGalleryUserTransformer();

  @Override
  public TechnologyRecommendation transformFrom(TechnologyRecommendationTO arg0) {
    if (arg0.getActive()) {
      TechnologyRecommendation product = new TechnologyRecommendation();
      product.setComment(commentConverter.fromTransientToEntity(arg0.getComment()));
      product.setActive(arg0.getActive());
      product.setScore(arg0.getScore());
      product.setTechnology(techTransformer.transformFrom(arg0.getTechnology()));
      product.setRecommender(tgUserTransformer.transformFrom(arg0.getRecommender()));
      return product;
    } else {
      return null;
    }
  }

  @Override
  public TechnologyRecommendationTO transformTo(TechnologyRecommendation arg0) {
    if (arg0.getActive()) {
      TechnologyRecommendationTO product = new TechnologyRecommendationTO();
      product.setComment(TechnologyCommentConverter.fromEntityToTransient(arg0.getComment()));
      product.setActive(arg0.getActive());
      product.setScore(arg0.getScore());
      product.setTechnology(techTransformer.transformTo(arg0.getTechnology()));
      product.setRecommender(tgUserTransformer.transformTo(arg0.getRecommender()));
      return product;
    } else {
      return null;
    }
  }

}
