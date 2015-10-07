package com.ciandt.techgallery.service.util;

import com.google.api.server.spi.config.Transformer;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.service.model.EndorsementEntityResponse;

public class EndorsementTransformer implements Transformer<Endorsement, EndorsementEntityResponse> {

  @Override
  public Endorsement transformFrom(EndorsementEntityResponse arg0) {
    Endorsement product = new Endorsement();
    product.setId(arg0.getId());
    product.setId(arg0.getId());
    product.setEndorser(Ref.create(arg0.getEndorser()));
    product.setEndorsed(Ref.create(arg0.getEndorsed()));
    product.setTimestamp(arg0.getTimestamp());
    product.setTechnology(Ref.create(arg0.getTechnology()));
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
