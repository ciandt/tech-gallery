package com.ciandt.techgallery.service.impl;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.TechnologyFollowersDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyFollowersDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;
import com.ciandt.techgallery.service.TechnologyFollowersService;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciant.techgallery.transaction.Transactional;

import java.util.ArrayList;

/**
 * Services for Technology Endpoint requests.
 *
 * @author felipers
 *
 */
@Transactional
public class TechnologyFollowersServiceImpl implements TechnologyFollowersService {

  /*
   * Attributes --------------------------------------------
   */
  private static TechnologyFollowersServiceImpl instance;

  UserServiceTG userService = UserServiceTGImpl.getInstance();
  TechnologyService techService = TechnologyServiceImpl.getInstance();
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
  @Transactional
  public Technology followTechnology(String technologyId, TechGalleryUser techUser)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    Technology technology = techService.getTechnologyById(technologyId, null);
    TechnologyFollowers technologyFollowers = followersDao.findById(technology.getId());

    if (technologyFollowers == null
        || !technologyFollowers.getFollowers().contains(Ref.create(techUser))) {
      technologyFollowers = follow(technologyFollowers, techUser, technology);
    } else if (technologyFollowers != null
        && technologyFollowers.getFollowers().contains(Ref.create(techUser))) {
      technologyFollowers = unfollow(technologyFollowers, techUser, technology);
    }
    update(technologyFollowers);
    userService.updateUser(techUser);

    return technology;
  }

  private TechnologyFollowers unfollow(TechnologyFollowers technologyFollowers,
      TechGalleryUser techUser, Technology technology) throws BadRequestException {
    technologyFollowers.getFollowers().remove(Ref.create(techUser));
    techUser.getFollowedTechnologyIds().remove(technology.getId());
    if (technologyFollowers.getFollowers().isEmpty()) {
      followersDao.delete(technologyFollowers);
      return null;
    }
    return technologyFollowers;
  }

  @Override
  public TechnologyFollowers follow(TechnologyFollowers technologyFollowers,
      TechGalleryUser techUser, Technology technology) {
    if (technologyFollowers == null) {
      technologyFollowers = new TechnologyFollowers();
      technologyFollowers.setId(technology.getId());
      technologyFollowers.setTechnology(Ref.create(technology));
      technologyFollowers.setFollowers(new ArrayList<Ref<TechGalleryUser>>());
    }
    technologyFollowers.getFollowers().add(Ref.create(techUser));
    if (techUser.getFollowedTechnologyIds() == null) {
      techUser.setFollowedTechnologyIds(new ArrayList<String>());
    }
    techUser.getFollowedTechnologyIds().add(technology.getId());
    return technologyFollowers;
  }

  @Override
  public void update(TechnologyFollowers technologyFollowers) throws BadRequestException {
    if (technologyFollowers != null) {
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
  }

  @Override
  public TechnologyFollowers findById(String id) {
    return followersDao.findById(id);
  }
}
