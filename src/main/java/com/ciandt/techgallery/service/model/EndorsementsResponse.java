package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.Endorsement;

import java.util.List;

/**
 * Response with all endorsement entities.
 * 
 * @author felipers
 *
 */
public class EndorsementsResponse implements Response {

  /** list with all endorsements. */
  List<Endorsement> endorsements;

  public List<Endorsement> getEndorsements() {
    return endorsements;
  }

  public void setEndorsements(List<Endorsement> endorsements) {
    this.endorsements = endorsements;
  }

}
