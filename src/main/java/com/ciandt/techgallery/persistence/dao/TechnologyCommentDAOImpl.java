package com.ciandt.techgallery.persistence.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;

/**
 * Class that implements DAO of TechnologyComment
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
public class TechnologyCommentDAOImpl extends GenericDAOImpl<TechnologyComment, Long>
    implements TechnologyCommentDAO {

  @Override
  public TechnologyComment findAllActiviesByTechnology(Technology technology) {
    Objectify objectify = OfyService.ofy();
    TechnologyComment entity = objectify.load().type(TechnologyComment.class)
        .filter("technology", Ref.create(technology)).filter("active", Boolean.TRUE).first().now();

    return entity; 
  }
}