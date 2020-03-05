package integrationTestsArquillian;

import java.net.URL;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.TestMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.DAO.CallDataDAO;
import com.ait.DataFileImport.FileData;
import com.ait.TDMBDDService.CountImsiFailureDurationDTO;
import com.ait.TDMBDDService.EventCauseDTO;
import com.ait.TDMBDDService.TDMBDDService;
import com.ait.callData.BaseData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
 


@RunWith(Arquillian.class)
public class RESTImportTest {

	
	 @Deployment
	 public static Archive<?> createTestArchive() {      
 		 return ShrinkWrap.create(WebArchive.class,"RestInteg.war")
 				  .addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
				  .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				  .addPackage(FileData.class.getPackage())
				  .addPackage(BaseData.class.getPackage())
				  .addPackage(TDMBDDService.class.getPackage())
				  .addPackages(true, "org.apache.poi")
				  .addClasses(CallDataDAO.class)
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
	 public void restAssuredImportTest() {
    	
    	RestAssured
    	.given()
    		.contentType(ContentType.TEXT).body(new String("QueryTestDataSet.xls"))
        .when()
        	.post(basePath +"rest/TelcoDataMgt")
        .then()
        	.statusCode(200);
    			// need to beef assertion - or have lower level units protecting this
    			// note num of errors returned needs to be added here too and woudl be a good assertion
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
    
   
 
    // Using loaded file above for query tests
   @Test
    @RunAsClient
    public void Test_GetAllEventAndCauseCodeByImsiSuccess(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/344930000000012")
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
    public void Test_getAllEventAndCauseCodeByIMSIEmptyResponse(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/34493000009999")
    			.then()
    				.statusCode(200).body("$", Matchers.hasSize(0));
    				
    }
    
    
    @Test
    @RunAsClient
    public void Test_getIMSIsWithinDates(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/IMSIsWithinDates?startDate=2020/01/11T14:00:00&endDate=2020/01/11T22:00:00")
    				//														  ?startDate=2020/01/11T14:00:00&endDate=2020/01/11T22:00:00
    			.then()
	   				.statusCode(200).body("$",Matchers.hasSize(5));       			   											
    }
    
        
    @Test
    @RunAsClient
    public void Test_CallFailureCountByPhoneModel(){
    	RestAssured
    			.given()
    		
    			.when()
    				.get(basePath +"rest/TelcoDataMgt" + "/CallFailureCountByPhoneModel?ueType=21060800&startDate=2020/01/11T17:14:00&endDate=2020/11/01T17:20:00")
    				//														                            ?startDate=2020/01/11T14:00:00&endDate=2020/01/11T17:19:00

    			.then()
	   				.statusCode(200).body("$",Matchers.hasItem(7));    
    }
   
    @Test
       @RunAsClient
       public void Test_CountOfIMSIFailureAndDuration(){
       	RestAssured
       			.given()
       		
       			.when()
       				.get(basePath +"rest/TelcoDataMgt" + "/CountOfIMSIFailureAndDuration?startDate=2020/01/23T14:00:00&endDate=2020/01/23T14:07:00")
       			.then()
	       			.statusCode(200).body("$",Matchers.hasSize(4));
       	//		.statusCode(200).body("$",Matchers.hasItem("[777770000000011, 1, 1000]"));


       	//		.statusCode(200).body("$",Matchers.hasItems("[[777770000000011, 1, 1000], [777770000000012, 2, 2000], [777770000000013, 3, 3000], [777770000000014, 1, 1000]]"));
		//		.statusCode(200).body("$",Matchers.hasItem(Matchers.hasItem("777770000000011")));
       	
       	//		.statusCode(200).body("$",Matchers.containsInAnyOrder(new String[]{"[777770000000011, 1, 1000]","[777770000000012, 2, 2000]","[777770000000013, 3, 3000]", "[777770000000014, 1, 1000]"}));
       				//  jsonArray??  --  https://stackoverflow.com/questions/39847564/check-output-of-jsonpath-with-hamcrest-matchers
       	
						//.sameJSONAs("[[777770000000011, 1, 1000], [777770000000012, 2, 2000], [777770000000013, 3, 3000], [777770000000014, 1, 1000]]"));

       					// now need better matchers
       					// json would be great here and karate
       					// else look into matchers doing this on my lists
       	
       }
}
