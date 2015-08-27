package com.ciandt.techgallery.sample.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.sample.persistence.model.Card;

/**
 * CardEndpoint Interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface CardEndpoint {

  public List<Card> listCards();

  public Card insertCard(Card card);

}
