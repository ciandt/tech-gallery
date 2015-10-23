package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.service.enums.DateFilterEnum;

/**
 * Response with all technology entities.
 * 
 * @author Thulio Ribeiro
 *
 */
public class TechnologyFilter implements Response {

  /** string for search in title. */
  private String titleContains;

  /** string for search in shortDescription. */
  private String shortDescriptionContains;

  /** string for search in recommendation. */
  private String recommendationIs;

  /** string for order option. */
  private String orderOptionIs;

  /** string for order option. */
  private DateFilterEnum dateFilter;

  public String getTitleContains() {
    return titleContains;
  }

  public String getRecommendationIs() {
    return recommendationIs;
  }

  public void setRecommendationIs(String recommendationIs) {
    this.recommendationIs = recommendationIs;
  }

  public void setTitleContains(String titleContains) {
    this.titleContains = titleContains;
  }

  public String getShortDescriptionContains() {
    return shortDescriptionContains;
  }

  public DateFilterEnum getDateFilter() {
    return dateFilter;
  }

  public void setDateFilter(DateFilterEnum dateFilter) {
    this.dateFilter = dateFilter;
  }

  public void setShortDescriptionContains(String shortDescriptionContains) {
    this.shortDescriptionContains = shortDescriptionContains;
  }

  public String getOrderOptionIs() {
    return orderOptionIs;
  }

  public void setOrderOptionIs(String orderOptionIs) {
    this.orderOptionIs = orderOptionIs;
  }

  public TechnologyFilter() {
  }

  /**
   * Construtor for TechnologyFilter.
   * 
   * @param titleContains
   *          part of the technology's title
   * @param shortDescriptionContains
   *          titleContains part of the technology's short description
   * @param recommendationIs
   *          technology's Ci&T Recommendation
   * @param orderOptionIs
   *          for sort the result
   */
  public TechnologyFilter(String titleContains, String shortDescriptionContains, String recommendationIs,
      Integer dateFilter, String orderOptionIs) {
    this.titleContains = titleContains;
    this.shortDescriptionContains = shortDescriptionContains;
    this.recommendationIs = recommendationIs;
    this.orderOptionIs = orderOptionIs;
    if (dateFilter != null && (dateFilter >= 0 && dateFilter <= 2)) {
      this.dateFilter = DateFilterEnum.values()[dateFilter];
    }
  }
}
