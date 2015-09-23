package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;

import java.util.List;

/**
 * Interface of TechnologyComment
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 22/09/2015
 *
 */
public interface TechnologyCommentDAO extends GenericDAO<TechnologyComment, Long> {
  List<TechnologyComment> findAllActivesByTechnology(Technology technology);
}
