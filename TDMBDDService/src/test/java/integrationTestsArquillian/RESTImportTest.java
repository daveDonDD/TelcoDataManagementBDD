package integrationTestsArquillian;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Query;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;


import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.*;

import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
import org.codehaus.groovy.*;

import com.ait.DAO.CallDataDAO;
import com.ait.TDMBDDService.*;
import com.ait.callData.*;


import com.ait.DataFileImport.*;
 


@RunWith(Arquillian.class)
public class RESTImportTest {

	
	 @Deployment
	 public static Archive<?> createTestArchive() {      
 		 return ShrinkWrap.create(WebArchive.class,"RestInteg.war")
 				  .addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
				  .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				  .addPackage(FileData.class.getPackage())
				  .addPackage(BaseData.class.getPackage())
				  .addPackages(true, "org.apache.poi")
				  .addClasses(
							 CallDataDAO.class,   
							 TDMBDDService.class     
							
							 )
			      .setWebXML("test_web.xml");   
		}
	 			
	 
	@EJB
	private CallDataDAO callDataDAO;
		

	@PersistenceContext
	private EntityManager em;
	 
	@ArquillianResource
    URL basePath;
 
	private FileData fileData;

    @Test 
    @RunAsClient
	public void restAssuredHelloWorldTest() {

        RestAssured.when().
        		get(basePath + "rest/TelcoDataMgt/HelloWorldTest").
               // http://localhost:8080/RestTest/rest/TelcoDataMgt/HelloWorldTest").
        then().
        		statusCode(200);
    }
  
        
        
    @Test 
    @RunAsClient
	public void restAssuredImportTestFilenotFound() {
    	
    	RestAssured
    	.given()
    		.contentType(ContentType.TEXT).body(new String("WrongFile.xls"))
        .when()
        	.post(basePath +"rest/TelcoDataMgt")
        .then()
        	.statusCode(406);
    	
    }        
    
    @Test 
    @RunAsClient
	public void restAssuredImportTest() {
    	
    	RestAssured
    	.given()
    		.contentType(ContentType.TEXT).body(new String("TestDataSet.xls"))
        .when()
        	.post(basePath +"rest/TelcoDataMgt")
        .then()
        	.statusCode(200);
    	// need to beef up assertions here - 
    }
 
    // Using loaded file above for query tests
    @Ignore
    @Test
    @RunAsClient
    public void Test_US4GetAllEventAndCauseCodeByImsiSuccess2IMSIs(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/310560000000012")
    			.then()
    				.statusCode(200).body("$", Matchers.hasSize(2));
			
//    	check correct user story!!!!!!
//expected '[[344930000000011, 1, 1000]]'
//Actual: [[344930000000011, 1, 1000]]   need to check this
// format returned is List of query (SELECT imsi, COUNT(*), SUM(duration)) e.g. [[310560000000012,2,2000]]
// so assertion is second element equals 2 
// or just hardcoded
// else use object serilaisation to convert back to pojo adn use from there
// but into other problem domain - if we had  json here this would be a no brainer.
//Karate simpler to manage flat json (or returned data in what ever format) and assert against that
}
    
    
    @Test
    @RunAsClient
    public void Test_US4_getAllEventAndCauseCodeByIMSIEmptyResponse(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/34493000009999")
    			.then()
    				.statusCode(200).body("$", Matchers.hasSize(0));
    				
    }
    
    @Ignore
    @Test
    @RunAsClient
    public void Test_US5_getIMSIsWithinDates(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/getIMSIsWithinDates?startDate=2020/01/11T17:16:00&endDate=2020/11/01T17:18:00")
    			.then()
	   				.statusCode(200).body("$",Matchers.hasSize(3));    // TestDataSet.xls has 3 in this time window   			   											
    }
    
   @Ignore
    @Test
    @RunAsClient
    public void Test_US6CallFailureCountByPhoneModel(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/CallFailureCountByPhoneModel?ueType=21060800&startDate=2020/01/11T17:14:00&endDate=2020/11/01T17:16:00")
    			.then()
	   				.statusCode(200).body("$",Matchers.hasSize(3));    // TestDataSet.xls has 2 in this time window
    }
}
