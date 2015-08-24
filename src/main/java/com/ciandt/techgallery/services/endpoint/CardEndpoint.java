package com.ciandt.techgallery.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.persistence.model.Card;

/**
 * CardEndpoint Interface.
 * 
 * @author felipegc
 *
 */
public interface CardEndpoint {

  public List<Card> listCards();

  public Card insertCard(Card card);

}
