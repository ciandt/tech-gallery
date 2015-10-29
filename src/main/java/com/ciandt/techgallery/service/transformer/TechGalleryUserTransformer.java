package com.ciandt.techgallery.service.transformer;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.UserResponse;
import com.google.api.server.spi.config.Transformer;

public class TechGalleryUserTransformer implements Transformer<TechGalleryUser, UserResponse> {

  @Override
  public TechGalleryUser transformFrom(UserResponse arg0) {
    TechGalleryUser product = new TechGalleryUser();
    product.setEmail(arg0.getEmail());
    product.setId(arg0.getId());
    product.setName(arg0.getName());
    product.setPhoto(arg0.getPhoto());
    product.setFollowedTechnologyIds(arg0.getFollowedTechIds());
    product.setAdmin(arg0.isAdmin());
    product.setPostGooglePlusPreference(arg0.getPostGooglePlusPreference());
    return product;
  }

  @Override
  public UserResponse transformTo(TechGalleryUser arg0) {
    if (arg0.getInactivatedDate() == null) {
      UserResponse product = new UserResponse();
      product.setEmail(arg0.getEmail());
      product.setId(arg0.getId());
      product.setName(arg0.getName());
      product.setPhoto(arg0.getPhoto());
      product.setFollowedTechIds(arg0.getFollowedTechnologyIds());
      product.setAdmin(arg0.isAdmin());
      product.setPostGooglePlusPreference(arg0.getPostGooglePlusPreference());
      return product;
    } else {
      return null;
    }
  }

}
