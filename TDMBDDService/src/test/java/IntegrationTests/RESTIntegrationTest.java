package IntegrationTests;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Query;
import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
//import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

//import org.jboss.arquillian.extension.rest.client.*;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;

import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.codehaus.groovy.*;

import com.ait.DAO.CallDataDAO;
import com.ait.TDMBDDService.TDMBDDService;
import com.ait.callData.*;


import com.ait.DataFileImport.*;
 

@RunWith(Arquillian.class)
public class RESTIntegrationTest {
	
	 @Deployment
	 public static WebArchive createDeployment() {
		 return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(TDMBDDService.class)
			    .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		}

	@ArquillianResource
	protected URL appUrl;
	 
    @Test 
    @RunAsClient
	public void restAssuredTDMBDDServiceRestHelloWorldTest() {

        RestAssured.when().
        		get("/test/rest/TelcoDataMgt/HelloWorldTest").
               // http://localhost:8080/test/rest/TelcoDataMgt/HelloWorldTest").
        then().
        		statusCode(200);
    }
    

    /*
	@Test
	@RunAsClient
	public void testRestTDMBDDServiceHelloWorld(@ArquillianResteasyResource final WebTarget webTarget)
	{
	    final Response response = webTarget	    		
	        .path("/TelcoDataMgt/HelloWorldTest")
	        .request(MediaType.APPLICATION_JSON).
	        get();	    
		assertEquals(response.getStatus(), 200);
	}
	
	*/
    
	/*
	@Test
	@RunAsClient
	public void testRestTDMBDDServiceImportFiles(
	    @ArquillianResteasyResource final WebTarget webTarget)
	{
		
		
	    final Response response = webTarget
	        .path("/TelcoDataMgt")
	        .request(MediaType.TEXT_PLAIN)
	        .post(Entity.text("src/test/resources/testData/TestDataset.xls"));
		assertEquals(response.getStatus(), 200);
	}
*/
}
