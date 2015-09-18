package com.ciandt.techgallery.sample.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.persistence.dao.CardDAO;
import com.ciandt.techgallery.sample.persistence.dao.CardDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.Card;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * UserGroupEndpoint Implementation class.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Api(name = "cardEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
    Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class CardEndpointImpl implements CardEndpoint {

  @Override
  public List<Card> listCards() {
    CardDAO cardDAO = new CardDAOImpl();
    List<Card> allCards = cardDAO.findAll();
    return allCards;
  }

  @ApiMethod(name = "card.inserts", httpMethod = "post")
  @Override
  public Card insertCard(Card card) {
    CardDAO cardDAO = new CardDAOImpl();

    Card response = new Card();
    cardDAO.addCard(card);

    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(card.getName());
    response.setName(responseBuilder.toString());
    return response;
  }
}
