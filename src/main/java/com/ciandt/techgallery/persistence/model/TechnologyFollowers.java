package com.ciandt.techgallery.persistence.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.List;

/**
 * TechnologyFolowers entity.
 * 
 * @author ibrahim
 *
 */
@Entity
public class TechnologyFollowers extends BaseEntity<String> {

  /*
   * Constants --------------------------------------------
   */
  public static final String ID = "id";
  public static final String TECHNOLOGY = "technology";
  public static final String FOLLOWERS = "followers";

  /** Always the same Id as Technology. */
  @Id
  String id;
  /** Followed Technology. */
  @Index
  private Ref<Technology> technology;
  /** List of folowers. */
  @Unindex
  private List<Ref<TechGalleryUser>> followers;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public Ref<Technology> getTechnology() {
    return technology;
  }

  public void setTechnology(Ref<Technology> technology) {
    this.technology = technology;
  }

  public List<Ref<TechGalleryUser>> getFollowers() {
    return followers;
  }

  public void setFollowers(List<Ref<TechGalleryUser>> followers) {
    this.followers = followers;
  }
}
