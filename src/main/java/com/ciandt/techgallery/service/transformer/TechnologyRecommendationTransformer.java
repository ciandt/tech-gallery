package com.ciandt.techgallery.service.transformer;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public class TechnologyRecommendationTransformer
    implements Transformer<TechnologyRecommendation, TechnologyRecommendationTO> {

  @Override
  public TechnologyRecommendation transformFrom(TechnologyRecommendationTO arg0) {
    if (arg0.getActive() == null || arg0.getActive() == true) {
      TechnologyRecommendation product = new TechnologyRecommendation();
      product.setActive(true);
      product.setScore(arg0.getScore());
      if (arg0.getComment() != null) {
        Key<TechnologyComment> commentKey =
            Key.create(TechnologyComment.class, arg0.getComment().getId());
        product.setComment(Ref.create(commentKey));
      } else {
        product.setComment(null);
      }
      if (arg0.getTechnology() != null) {
        Key<Technology> technologyKey = Key.create(Technology.class, arg0.getTechnology().getId());
        product.setTechnology(Ref.create(technologyKey));
      } else {
        product.setTechnology(null);
      }
      if (arg0.getRecommender() != null) {
        Key<TechGalleryUser> tgUserKey = Key.create(arg0.getRecommender().getEmail());
        product.setRecommender(Ref.create(tgUserKey));
      } else {
        product.setRecommender(null);
      }
      return product;
    } else {
      return null;
    }
  }

  @Override
  public TechnologyRecommendationTO transformTo(TechnologyRecommendation arg0) {
    if (arg0.getActive()) {
      TechnologyRecommendationTO product = new TechnologyRecommendationTO();
      product.setComment(arg0.getComment().get());
      product.setActive(arg0.getActive());
      product.setScore(arg0.getScore());
      product.setTechnology(arg0.getTechnology().get());
      product.setRecommender(arg0.getRecommender().get());
      return product;
    } else {
      return null;
    }
  }

}
