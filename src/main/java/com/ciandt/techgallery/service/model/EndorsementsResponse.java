package com.ciandt.techgallery.service.model;

import java.util.List;

/**
 * Response with all endorsement entities.
 * 
 * @author felipers
 *
 */
public class EndorsementsResponse implements Response {

  /** list with all endorsements. */
  List<EndorsementResponse> endorsements;

  public List<EndorsementResponse> getEndorsements() {
    return endorsements;
  }

  public void setEndorsements(List<EndorsementResponse> endorsements) {
    this.endorsements = endorsements;
  }

}
