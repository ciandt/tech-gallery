package com.ciandt.techgallery.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.Technology;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

/**
 * EndorsementDAOImpl methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class EndorsementDAOImpl extends GenericDAOImpl<Endorsement, Long> implements EndorsementDAO {

  @Override
  public List<Endorsement> findAllByTechnology(Technology technology) {
    Objectify objectify = OfyService.ofy();
    List<Endorsement> entities =
        objectify.load().type(Endorsement.class).filter("technology", Ref.create(technology))
            .list();

    if (entities == null || entities.size() <= 0) {
      return new ArrayList<Endorsement>();
    }
    return entities;
  }

}
