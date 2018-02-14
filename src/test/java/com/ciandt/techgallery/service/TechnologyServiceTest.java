package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.impl.TechnologyServiceImpl;
import com.google.api.server.spi.response.BadRequestException;
import com.google.appengine.api.users.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TechnologyServiceTest {

	private TechnologyService service = TechnologyServiceImpl.getInstance();

	private User currentUser;

	@Before
	public void setUp() {
		currentUser = new User("test@example.com", "example.com");
	}

	@Test (expected = BadRequestException.class)
	public void shouldThrowBadRequestWhenInsertWithNoId() throws Exception {
		Technology technology = new Technology();
		technology.setName("Angular JS");
		technology.setDescription("Framework javascript");
		technology.setShortDescription("Framework javascript");

		service.addOrUpdateTechnology(technology, currentUser);
	}

	@Test (expected = BadRequestException.class)
	public void shouldThrowBadRequestWhenInsertWithNoName() throws Exception {
		Technology technology = new Technology();
		technology.setId(technology.convertNameToId("Angular JS"));
		technology.setDescription("Framework javascript");
		technology.setShortDescription("Framework javascript");

		service.addOrUpdateTechnology(technology, currentUser);
	}

	@Test
	public void shouldReturnLowerCaseWithNoSpecialCharacters() {
		Technology technology = new Technology();
		String convertedWithSpace = technology.convertNameToId("Angular JS");
		String convertedWithDot = technology.convertNameToId(".NET");
		assertThat("angular_js", is(equalTo(convertedWithSpace)));
		assertThat("net", is(equalTo(convertedWithDot)));
	}

}
