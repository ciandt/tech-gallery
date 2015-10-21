package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

import java.util.List;


public interface EmailService {
  void push(TechGalleryUser user, Technology technology,
      List<TechnologyRecommendation> recommendations, List<TechnologyComment> comments);

  void execute(String userId, String technologyId, String recommendationsIds, String commentsIds,
      String serverUrl);
}
