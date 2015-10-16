package com.ciandt.techgallery.service.transformer;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.EndorsementEntityResponse;

public class EndorsementTransformer implements Transformer<Endorsement, EndorsementEntityResponse> {

  @Override
  public Endorsement transformFrom(EndorsementEntityResponse arg0) {
    Endorsement product = new Endorsement();
    product.setId(arg0.getId());
    if (arg0.getEndorser() != null) {
      Key<TechGalleryUser> endorserKey =
          Key.create(TechGalleryUser.class, arg0.getEndorser().getId());
      product.setEndorser(Ref.create(endorserKey));
    } else {
      product.setEndorser(null);
    }
    if (arg0.getEndorsed() != null) {
      Key<TechGalleryUser> endorsedKey =
          Key.create(TechGalleryUser.class, arg0.getEndorsed().getId());
      product.setEndorsed(Ref.create(endorsedKey));
    } else {
      product.setEndorsed(null);
    }
    if (arg0.getTechnology() != null) {
      Key<Technology> techKey = Key.create(Technology.class, arg0.getTechnology().getId());
      product.setTechnology(Ref.create(techKey));
    } else {
      product.setTechnology(null);
    }
    product.setTimestamp(arg0.getTimestamp());
    product.setActive(arg0.isActive());
    return product;
  }

  @Override
  public EndorsementEntityResponse transformTo(Endorsement arg0) {
    EndorsementEntityResponse product = new EndorsementEntityResponse();
    product.setId(arg0.getId());
    product.setId(arg0.getId());
    product.setEndorser(arg0.getEndorserEntity());
    product.setEndorsed(arg0.getEndorsedEntity());
    product.setTimestamp(arg0.getTimestamp());
    product.setTechnology(arg0.getTechnologyEntity());
    product.setActive(arg0.isActive());
    return product;
  }

}
