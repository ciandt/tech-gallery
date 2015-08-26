package com.ciandt.techgallery.sample.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.persistence.model.Card;

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
