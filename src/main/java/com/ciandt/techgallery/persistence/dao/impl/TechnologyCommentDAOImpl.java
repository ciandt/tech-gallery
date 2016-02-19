package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;

import java.util.Date;
import java.util.List;

/**
 * Class that implements DAO of TechnologyComment
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
public class TechnologyCommentDAOImpl extends GenericDAOImpl<TechnologyComment, Long>
    implements TechnologyCommentDAO {

  private static TechnologyCommentDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private TechnologyCommentDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return TechnologyCommentDAOImpl instance.
   */
  public static TechnologyCommentDAOImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyCommentDAOImpl();
    }
    return instance;
  }

  @Override
  public List<TechnologyComment> findAllActivesByTechnology(Technology technology) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyComment> entities =
        objectify.load().type(TechnologyComment.class).order("-" + TechnologyComment.TIMESTAMP)
            .filter(TechnologyComment.TECHNOLOGY, Ref.create(technology))
            .filter(TechnologyComment.ACTIVE, Boolean.TRUE).list();

    return entities;
  }

  @Override
  public List<TechnologyComment> findAllCommentsStartingFrom(Technology technology, Date date) {
    final Objectify objectify = OfyService.ofy();
    final List<TechnologyComment> comments =
        objectify.load().type(TechnologyComment.class)
            .filter(TechnologyComment.TIMESTAMP + " >", date)
            .filter(TechnologyComment.TECHNOLOGY, technology)
            .filter(TechnologyComment.ACTIVE, Boolean.TRUE).list();
    if (comments == null || comments.size() <= 0) {
      return null;
    }
    return comments;
  }
}
