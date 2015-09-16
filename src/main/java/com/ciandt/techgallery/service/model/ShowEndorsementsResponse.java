package com.ciandt.techgallery.service.model;

import java.util.List;

/**
 * Response with Endorsements grouped by endorsed. Uses the transient.
 * 
 * @author Daniel Eduardo
 *
 */
public class ShowEndorsementsResponse implements Response {

  List<EndorsementsGroupedByEndorsedTransient> endorsements;

  public List<EndorsementsGroupedByEndorsedTransient> getEndorsements() {
    return endorsements;
  }

  public void setEndorsements(List<EndorsementsGroupedByEndorsedTransient> endorsements) {
    this.endorsements = endorsements;
  }

}
