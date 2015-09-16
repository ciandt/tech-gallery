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
  List<EndorsementEntityResponse> endorsements;

  public List<EndorsementEntityResponse> getEndorsements() {
    return endorsements;
  }

  public void setEndorsements(List<EndorsementEntityResponse> endorsements) {
    this.endorsements = endorsements;
  }

}
