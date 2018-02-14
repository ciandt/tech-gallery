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
        // given - pesquiso e nao encontro a tecnologia
//        TechnologyFilter filter = new TechnologyFilter();
//        filter.setTitleContains("angular");
//        TODO: not allow test findTechnologiesByFilter with no real user
//        TODO: not is possible mock singleton instance and private methods
//        TechnologiesResponse response = (TechnologiesResponse) service.findTechnologiesByFilter(filter, currentUser);
//        List notFoundedTechnologies = response.getTechnologies();
//        assertNotNull(notFoundedTechnologies);

        // when - adiciono novo technologia
        Technology technology = new Technology();
        technology.setId(technology.convertNameToId("Angular JS"));
        technology.setName("Angular JS");
        technology.setDescription("Framework javascript");
        technology.setShortDescription("Framework javascript");

        // add imagem content - TODO: storage bucket not work using unit test
        // technology.setImageContent("sample base64 imagem data");
        service.addOrUpdateTechnology(technology, currentUser);

        // then - verifica se a technologia foi inserida
        // and - sou o author da mesma
        // and - contadores de endorsement e
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
