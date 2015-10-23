package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;


public interface EmailService {
  void push(TechGalleryUser user, Technology technology, String recommendationsIds,
      String commentsIds, String isLastItem, String keyCronJob);

  void execute(String userId, String technologyId, String recommendationsIds, String commentsIds,
      String isLastItem, String keyCronJob, String serverUrl);

  void sendDailyEmailtoFollowers();
}
