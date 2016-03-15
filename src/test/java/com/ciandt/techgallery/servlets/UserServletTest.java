package com.ciandt.techgallery.servlets;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.googlecode.objectify.ObjectifyService;

/**
 * Created by jneves on 08/03/16.
 */
public class UserServletTest {

  private final LocalServiceTestHelper helper =
          new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private UserServlet servlet = new UserServlet();
  private HttpServletRequest request;
  private HttpServletResponse response;


  /**
   * Setup method for the test.
   */
  @Before
  public void setUp() {

    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    helper.setUp();
    ObjectifyService.begin();

  }

  @Test
  public void withParameterCsvReturnCsvFile() throws Exception {

    when(request.getParameter("type")).thenReturn("csv");
    servlet.doGet(request, response);
    verifyCsvFile();
  }

  @Test
  public void withoutParameterReturnCsvFile() throws Exception {

    when(request.getParameter("type")).thenReturn("");
    servlet.doGet(request, response);
    verifyCsvFile();
  }

  @Test
  public void withParameterXlsReturnXlsFile() throws Exception {

    when(request.getParameter("type")).thenReturn("xls");

    servlet.doGet(request, response);

    verify(request, atLeast(1)).getParameter("type");
    verify(response, atLeast(1)).setContentType("application/ms-excel");
    verify(response, atLeast(1)).setHeader("Content-Disposition",
            "attachment; filename=\"Colaboradores.xls\"");
  }

  private void verifyCsvFile() {
    verify(request, atLeast(1)).getParameter("type");
    verify(response, atLeast(1)).setContentType("text/csv");
    verify(response, atLeast(1)).setHeader("Content-Disposition",
            "attachment; filename=\"Colaboradores.csv\"");
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

}
