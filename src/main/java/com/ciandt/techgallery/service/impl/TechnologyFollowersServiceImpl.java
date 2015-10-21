package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;

import com.ciandt.techgallery.persistence.dao.TechnologyFollowersDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyFollowersDAOImpl;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;
import com.ciandt.techgallery.service.TechnologyFollowersService;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;

/**
 * Services for Technology Endpoint requests.
 *
 * @author felipers
 *
 */
public class TechnologyFollowersServiceImpl implements TechnologyFollowersService {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyFollowersServiceImpl instance;

  TechnologyFollowersDAO followersDao = TechnologyFollowersDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private TechnologyFollowersServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author ibrahim
   *
   * @return TechnologyFollowersServiceImpl instance.
   */
  public static TechnologyFollowersServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyFollowersServiceImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public TechnologyFollowers getTechnologyFollowersByTechnology(final Technology technology)
      throws BadRequestException {
    if (technology == null) {
      throw new BadRequestException(ValidationMessageEnums.NO_TECHNOLOGY_WAS_FOUND.message());
    } else {
      return followersDao.findByTechnology(technology);
    }
  }

  @Override
  public void update(TechnologyFollowers technologyFollowers) throws BadRequestException {
    if (technologyFollowers.getTechnology() == null) {
      throw new BadRequestException(ValidationMessageEnums.TECHNOLOGY_ID_CANNOT_BLANK.message());
    }
    if (technologyFollowers.getFollowers() == null
        || technologyFollowers.getFollowers().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.FOLLOWERS_CANNOT_EMPTY.message());
    }
    if (technologyFollowers.getId() != null
        && followersDao.findById(technologyFollowers.getId()) != null) {
      followersDao.update(technologyFollowers);
    } else {
      followersDao.add(technologyFollowers);
    }
  }

  @Override
  public void delete(TechnologyFollowers technologyFollowers) {
    followersDao.delete(technologyFollowers);
  }

}
