package com.ciandt.techgallery.service.util;

import com.google.api.server.spi.config.Transformer;
import com.google.api.server.spi.response.NotFoundException;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyCommentService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.impl.TechnologyCommentServiceImpl;
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.ciandt.techgallery.service.impl.UserServiceTGImpl;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public class TechnologyRecommendationTransformer
    implements Transformer<TechnologyRecommendation, TechnologyRecommendationTO> {

  private TechnologyService techService = TechnologyServiceImpl.getInstance();
  private UserServiceTG tgUserService = UserServiceTGImpl.getInstance();
  private TechnologyCommentService commentService = TechnologyCommentServiceImpl.getInstance();

  @Override
  public TechnologyRecommendation transformFrom(TechnologyRecommendationTO arg0) {
    if (arg0.getActive() == null || arg0.getActive() == true) {
      TechnologyRecommendation product = new TechnologyRecommendation();
      product.setActive(true);
      product.setScore(arg0.getScore());
      try {
        TechnologyComment comment = commentService.getById(arg0.getComment().getId());
        Ref<TechnologyComment> commentRef = Ref.create(comment);
        product.setComment(commentRef);
      } catch (NotFoundException e) {
        product.setTechnology(null);
      }
      try {
        Technology technology = techService.getTechnologyById(arg0.getTechnology().getId());
        Ref<Technology> technologyRef = Ref.create(technology);
        product.setTechnology(technologyRef);
      } catch (NotFoundException e) {
        product.setTechnology(null);
      }
      try {
        TechGalleryUser tgUser = tgUserService.getUserByEmail(arg0.getRecommender().getEmail());
        Ref<TechGalleryUser> refTgUser = Ref.create(tgUser);
        product.setRecommender(refTgUser);
      } catch (NotFoundException e) {
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
