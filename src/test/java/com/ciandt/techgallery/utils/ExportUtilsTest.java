package com.ciandt.techgallery.utils;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.common.collect.Lists;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.persistence.model.profile.UserProfileItem;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jneves on 11/03/16.
 */
public class ExportUtilsTest {

  private static final String CSV_HEADERS = "\"Login\",\"Nome\",\"Tecnologia\",\"Total de Indicações\",\"Auto-Avaliação\"\n";
  List<UserProfile> listUserProfile = Lists.newArrayList();
  TechGalleryUser techGalleryUser;
  TechGalleryUser techGalleryUser2;
  UserProfile userProfile;
  UserProfile userProfile2;

  private final LocalServiceTestHelper helper =
          new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  /**
   * Setup method for the test.
   */
  @Before
  public void setUp() {

    helper.setUp();
    ObjectifyService.register(TechGalleryUser.class);
    ObjectifyService.register(UserProfile.class);
    ObjectifyService.register(Technology.class);
    ObjectifyService.begin();

    createProfiles();

    ObjectifyService.ofy().save().entity(techGalleryUser).now();
    ObjectifyService.ofy().save().entity(techGalleryUser2).now();
    ObjectifyService.ofy().save().entity(userProfile).now();
    ObjectifyService.ofy().save().entity(userProfile2).now();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void createCsv() throws Exception {

    List<UserProfile> user = UserProfileServiceImpl.getInstance().findAllUsersProfile();

    String csv = new String(ExportUtils.createCsvUsersProfile(user));
    //StringWriter csv = ExportUtils.createCsvUsersProfile(user);
    assertNotNull(csv);

    String expectedCsv = CSV_HEADERS +
            "\"beltrano\",\"Beltrano Oliveira\",\"Spring MVC\",\"15.0\",\"8.0\"\n" +
            "\"beltrano\",\"Beltrano Oliveira\",\"Angular Js\",\"10.0\",\"5.0\"\n" +
            "\"fulano\",\"Fulano da Silva\",\"Spring MVC\",\"15.0\",\"8.0\"\n" +
            "\"fulano\",\"Fulano da Silva\",\"Angular Js\",\"10.0\",\"5.0\"\n";

    assertThat(csv.toString(), CoreMatchers.containsString(expectedCsv));
  }

  @Test
  public void createCsvWithBlankValues() throws Exception {

    techGalleryUser.setEmail(null);
    techGalleryUser2.setEmail(null);

    List<UserProfile> user = UserProfileServiceImpl.getInstance().findAllUsersProfile();
    String csv = new String(ExportUtils.createCsvUsersProfile(user));
    assertNotNull(csv);

    String expectedCsv = CSV_HEADERS +
            "\"\",\"Beltrano Oliveira\",\"Spring MVC\",\"15.0\",\"8.0\"\n" +
            "\"\",\"Beltrano Oliveira\",\"Angular Js\",\"10.0\",\"5.0\"\n" +
            "\"\",\"Fulano da Silva\",\"Spring MVC\",\"15.0\",\"8.0\"\n" +
            "\"\",\"Fulano da Silva\",\"Angular Js\",\"10.0\",\"5.0\"\n";

    assertThat(csv.toString(), CoreMatchers.containsString(expectedCsv));
  }

  private void createProfiles() {

    techGalleryUser = new TechGalleryUser();
    techGalleryUser.setId(1l);
    techGalleryUser.setName("Fulano da Silva");
    techGalleryUser.setEmail("fulano@ciandt.com");

    techGalleryUser2 = new TechGalleryUser();
    techGalleryUser2.setId(2l);
    techGalleryUser2.setName("Beltrano Oliveira");
    techGalleryUser2.setEmail("beltrano@ciandt.com");

    userProfile = new UserProfile();
    userProfile.setId("fulano");
    userProfile.setOwner(Ref.create(techGalleryUser));

    userProfile2 = new UserProfile();
    userProfile2.setId("beltrano");
    userProfile2.setOwner(Ref.create(techGalleryUser2));

    Technology technology = new Technology();
    technology.setId("angular_js");
    technology.setName("Angular Js");

    UserProfileItem userProfileItem = new UserProfileItem(technology);
    userProfileItem.setSkillLevel(5);
    userProfileItem.addToEndorsementsCounter(10);

    Technology technology2 = new Technology();
    technology2.setId("spring_mvn");
    technology2.setName("Spring MVC");

    UserProfileItem userProfileItem2 = new UserProfileItem(technology2);
    userProfileItem2.setSkillLevel(8);
    userProfileItem2.addToEndorsementsCounter(15);

    userProfile.addItem(UserProfile.POSITIVE_RECOMMENDATION, Key.create(technology), userProfileItem);
    userProfile.addItem(UserProfile.POSITIVE_RECOMMENDATION, Key.create(technology2), userProfileItem2);

    userProfile2.addItem(UserProfile.POSITIVE_RECOMMENDATION, Key.create(technology), userProfileItem);
    userProfile2.addItem(UserProfile.POSITIVE_RECOMMENDATION, Key.create(technology2), userProfileItem2);

    listUserProfile.add(userProfile);
    listUserProfile.add(userProfile2);

  }

}
