package service.technology;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.TechnologyService;
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class ShareTechnologyTest {
    private final static LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
                    .setEnvIsAdmin(true)
                    .setEnvIsLoggedIn(true)
                    .setEnvAuthDomain("localhost")
                    .setEnvEmail("example@google.com");

    private Closeable closeable;

    private TechnologyService service = TechnologyServiceImpl.getInstance();

    private User currentUser;

    @Before
    public void setUp() {
        SystemProperty.applicationVersion.set("test");
        helper.setUp();
        closeable = ObjectifyService.begin();
        currentUser = UserServiceFactory.getUserService().getCurrentUser();
    }

    @After
    public void tearDown() {
        closeable.close();
        helper.tearDown();
    }

    @Test
    public void newDescriptionWhenTechnologyWasUpdated() throws Exception {

        // given - technology was found
        Technology technology = new Technology();
        technology.setId(technology.convertNameToId("Angular JS"));
        technology.setName("Angular JS");
        technology.setDescription("Framework javascript");
        technology.setShortDescription("Framework javascript");

        Technology founded = service.addOrUpdateTechnology(technology, currentUser);

        // when - altero os atributos de descrição e website
        // and - salvo as alterações
        String description = "Updated Description";
        String url = "http://www.globo.com";
        founded.setDescription(description);
        founded.setWebsite(url);
        service.addOrUpdateTechnology(technology, currentUser);

        // then - confirmo a alteração dos atributos
        Technology updated = service.getTechnologyById(technology.getId(), currentUser);
        assertThat(updated.getDescription(), is(equalTo(description)));
        assertThat(updated.getWebsite(), is(equalTo(url)));
    }

    @Test
    public void addTechnologyWhenNotExists() throws Exception {

        // given - I added a new tecnology
        Technology technology = new Technology();
        technology.setId(technology.convertNameToId("Angular JS"));
        technology.setName("Angular JS");
        technology.setDescription("Framework javascript");
        technology.setShortDescription("Framework javascript");

        // add imagem content - TODO: storage bucket not work using unit test
        // technology.setImageContent("sample base64 imagem data");
        service.addOrUpdateTechnology(technology, currentUser);

        // then - check if technology was created
        // and - if I am an author
        // and - counters has been started with zero
        Technology founded = service.getTechnologyById(technology.getId(), currentUser);
        assertNotNull(founded);
        assertEquals(founded.getId(), technology.getId());
        assertEquals(founded.getName(), technology.getName());

        assertEquals(founded.getAuthor(), currentUser.getEmail());
        assertEquals(founded.getActive(), Boolean.TRUE);
        assertNotNull(founded.getCreationDate());
        assertNotNull(founded.getLastActivity());

        assertThat(founded.getEndorsersCounter(), is(equalTo(0)));
        assertThat(founded.getPositiveRecommendationsCounter(), is(equalTo(0)));
        assertThat(founded.getNegativeRecommendationsCounter(), is(equalTo(0)));
    }

}
