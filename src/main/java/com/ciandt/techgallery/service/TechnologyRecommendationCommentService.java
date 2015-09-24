package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologyCommentTO;
import com.ciandt.techgallery.service.model.TechnologyRecommendationTO;
import com.ciandt.techgallery.service.model.TechnologyResponse;

public interface TechnologyRecommendationCommentService {

  public Response addRecommendationComment(TechnologyRecommendationTO recommendationTO,
      TechnologyCommentTO commentTO, TechnologyResponse technology, User user)
          throws BadRequestException, InternalServerErrorException, NotFoundException;
}
