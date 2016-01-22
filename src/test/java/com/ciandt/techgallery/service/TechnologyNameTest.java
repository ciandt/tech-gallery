package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.Technology;

import org.junit.Assert;
import org.junit.Test;

public class TechnologyNameTest {

  @Test
  public void testTechID(){
    Technology tech = new Technology();
    tech.setName("#._/");
    Assert.assertEquals(tech.convertNameToId(tech.getName()), "___");
  }

}
