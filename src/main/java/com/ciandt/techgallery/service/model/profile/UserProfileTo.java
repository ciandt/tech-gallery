package com.ciandt.techgallery.service.model.profile;

import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.googlecode.objectify.Ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

public class UserProfileTo {

  private TechGalleryUser owner;

  private List<UserProfileItemTo> technologies;
  
  private HashMap<String, UserProfileItemTo> itemByTech; 

  /**
   * All args constructor.
   * 
   * @param owner the profile owner
   * @param positiveRecItems itens with positive recommendation by the owner
   * @param negativeRecItems itens with negative recommendation by the owner
   * @param otherItems itens which the owner interacted with, but did not recommended
   */
  public UserProfileTo(TechGalleryUser owner, List<UserProfileItem> positiveRecItems,
      List<UserProfileItem> negativeRecItems, List<UserProfileItem> otherItems) {
    super();
    this.owner = owner;
    this.itemByTech=new HashMap<String, UserProfileItemTo>();
    this.setTechnologies(new ArrayList<UserProfileItemTo>());
    
    populate(positiveRecItems, negativeRecItems, otherItems);
  }

  private void populate(List<UserProfileItem> positiveRecItems,
      List<UserProfileItem> negativeRecItems, List<UserProfileItem> otherItems) {
    
    UserProfileItemTo itemTo;
    String technologyName;
    if(positiveRecItems!=null){
      for (UserProfileItem userProfileItem : positiveRecItems) {
        technologyName = userProfileItem.getTechnologyName();
        itemTo = itemByTech.get(technologyName);
        if(itemTo==null){
          itemTo = transformToItemTo(userProfileItem, Boolean.TRUE);
          getTechnologies().add(itemTo);
          itemByTech.put(technologyName, itemTo);
        }
      }
    }
    if(negativeRecItems!=null){
      for (UserProfileItem userProfileItem : negativeRecItems) {
        technologyName = userProfileItem.getTechnologyName();
        itemTo = itemByTech.get(technologyName);
        if(itemTo==null){
          itemTo = transformToItemTo(userProfileItem, Boolean.FALSE);
          getTechnologies().add(itemTo);
          itemByTech.put(technologyName, itemTo);
        }
      }
    }
    if(otherItems!=null){
      for (UserProfileItem userProfileItem : otherItems) {
        technologyName = userProfileItem.getTechnologyName();
        itemTo = itemByTech.get(technologyName);
        if(itemTo==null){
          itemTo = transformToItemTo(userProfileItem, null);
          getTechnologies().add(itemTo);
          itemByTech.put(technologyName, itemTo);
        } else {
          transformComment(userProfileItem, itemTo);
        }
      }
    }
  }

  private UserProfileItemTo transformToItemTo(UserProfileItem userProfileItem,
      Boolean isPositive) {
    
    UserProfileItemTo userProfileItemTo = new UserProfileItemTo();
    if (isPositive!=null) {
      Technology tech = TechnologyDAOImpl.getInstance().findByName(userProfileItem.getTechnologyName());
      TechnologyRecommendation rec = TechnologyRecommendationDAOImpl.getInstance().findActiveByRecommenderAndTechnology(owner, tech);
      if(rec!=null){
        RecomendationTo recomendationTo = new RecomendationTo();
        recomendationTo.setPositive(rec.getScore());
        recomendationTo.setComment(rec.getComment().get().getComment());
        userProfileItemTo.setRecommendation(recomendationTo);
      }
    }
    
    transformComment(userProfileItem, userProfileItemTo);
    
   userProfileItemTo.setCompanyRecommendation(userProfileItem.getCompanyRecommendation());
   userProfileItemTo.setEndorsementsCount(userProfileItem.getEndorsementQuantity());
   userProfileItemTo.setSkillLevel(userProfileItem.getSkillLevel());
   userProfileItemTo.setTechnologyName(userProfileItem.getTechnologyName());
   userProfileItemTo.setTechnologyPhotoUrl(userProfileItemTo.getTechnologyPhotoUrl());
    
    return userProfileItemTo;
  }

  private void transformComment(UserProfileItem userProfileItem,
      UserProfileItemTo userProfileItemTo) {
    
    if (userProfileItem.getComments() != null){
      if(userProfileItemTo.getComments()==null){
        userProfileItemTo.setComments(new ArrayList<SubItemCommentTo>());
      }
      SubItemCommentTo commentTo;
      for(Ref<TechnologyComment> comm : userProfileItem.getComments()){
        commentTo = new SubItemCommentTo();
        commentTo.setBody(comm.get().getComment());
        commentTo.setTimestamp(comm.get().getTimestamp());
        userProfileItemTo.getComments().add(commentTo);
      }
    }
  }

  public TechGalleryUser getOwner() {
    return owner;
  }

  public void setOwner(TechGalleryUser owner) {
    this.owner = owner;
  }

  public List<UserProfileItemTo> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(List<UserProfileItemTo> technologies) {
    this.technologies = technologies;
  }



}
