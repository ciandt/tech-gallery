package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;

public interface TechnologyRecommendationService {

  public Response addRecommendation(TechnologyRecommendationTO recommendationTO, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException;

}
