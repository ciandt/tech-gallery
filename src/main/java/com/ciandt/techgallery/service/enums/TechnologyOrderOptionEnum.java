package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.persistence.model.Project;
import com.ciandt.techgallery.persistence.model.Technology;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Enum for mapping Order Options.
 * 
 * @author Felipe Ibrahim
 *
 */
public enum TechnologyOrderOptionEnum {

  POSITIVE_RECOMMENDATION_AMOUNT("Recomendações Positivas") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology counter1, Technology counter2) {
          return Integer.compare(counter2.getPositiveRecommendationsCounter(),
              counter1.getPositiveRecommendationsCounter());
        }
      });
    }
  },
  NEGATIVE_RECOMMENDATION_AMOUNT("Recomendações Negativas") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology counter1, Technology counter2) {
          return Integer.compare(counter2.getNegativeRecommendationsCounter(),
              counter1.getNegativeRecommendationsCounter());
        }
      });
    }
  },
  COMMENT_AMOUNT("Comentários") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology counter1, Technology counter2) {
          return Integer.compare(counter2.getCommentariesCounter(),
              counter1.getCommentariesCounter());
        }
      });
    }
  },
  ENDORSEMENT_AMOUNT("Indicações") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology counter1, Technology counter2) {
          return Integer.compare(counter2.getEndorsersCounter(), counter1.getEndorsersCounter());
        }
      });
    }
  },
  APHABETIC("Alfabética") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology counter1, Technology counter2) {
          return counter1.getName().compareTo(counter2.getName());
        }
      });
    }
  },
  BYPROJECT("Projeto") {
    @Override
    public void sort(List<Technology> techList) {
      Collections.sort(techList, new Comparator<Technology>() {
        @Override
        public int compare(Technology technology1, Technology technology2) {
          int result = Integer.MAX_VALUE;
          if(technology1.getProject() != null) {
            result = technology1.getProject().compareTo(technology2.getProject());
          }
          System.out.println("TechnologyOrderOptionEnum-> name1: " + technology1.getName());
          System.out.println("TechnologyOrderOptionEnum-> name2: " + technology2.getName());
          System.out.println("TechnologyOrderOptionEnum-> sortResult: " + result);
          return result;
        }
      });
    }
  };

  private String option;

  TechnologyOrderOptionEnum(String option) {
    this.option = option;
  }

  public String option() {
    return option;
  }

  /**
   * Convert the text informed to Enum.
   *
   * @param text to be converted.
   * 
   * @return the enum value.
   */
  public static TechnologyOrderOptionEnum fromString(String text) {
    if (text != null) {
      for (TechnologyOrderOptionEnum item : TechnologyOrderOptionEnum.values()) {
        if (text.equalsIgnoreCase(item.option())) {
          return item;
        }
      }
    }
    return null;
  }

  public abstract void sort(List<Technology> techList);
}
