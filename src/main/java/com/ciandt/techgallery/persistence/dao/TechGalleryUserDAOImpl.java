package com.ciandt.techgallery.persistence.dao;

import java.util.List;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.googlecode.objectify.Objectify;

/**
 * UserDAOImpl methods implementation.
 * 
 * @author bliberal
 *
 */
public class TechGalleryUserDAOImpl extends GenericDAOImpl<TechGalleryUser, Long> implements TechGalleryUserDAO {

  public TechGalleryUser findByGplusId(final String gplusId) {
    Objectify objectify = OfyService.ofy();
    List<TechGalleryUser> listTechGalleryUser = objectify.load().type(TechGalleryUser.class).filter("gplusId", gplusId).list();
    if(listTechGalleryUser==null)
       return null;
    else
      return listTechGalleryUser.get(0);
  }
  
}
