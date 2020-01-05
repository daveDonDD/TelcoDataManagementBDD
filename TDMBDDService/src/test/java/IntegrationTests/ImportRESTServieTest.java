package IntegrationTests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
 
//import org.apache.http.HttpStatus;
import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;

import com.ait.TDMBDDService.TDMBDDService;
import com.ait.callData.BaseData;
import com.ait.callData.CallDataDAO;





//@RunWith(Arquillian.class)
public class ImportRESTServieTest {
/*	
	@ArquillianResource
	 private URL deploymentURL;
	 
	@Deployment(testable = false)
	public static Archive createTestArchive() {
		return ShrinkWrap
			.create(WebArchive.class,"rest.war")
			.addClasses(TDMBDDService.class,CallDataDAO.class, BaseData.class)
			.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");		
	}
	
	
//	public static Archive<?> createTestArchive() {
//		return ShrinkWrap
//				.create(JavaArchive.class, "CallDataTest.jar")
//				.addClasses(CallDataDAO.class, BaseData.class)
//				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
//				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//	}

	
	@EJB
	private CallDataDAO callDataDAO;
	
	
	@PersistenceContext
    private EntityManager em;
	
	private List<BaseData> baseDataList;
	
	

	@Test
	public void testImportFile(
			@ArquillianResteasyResource TDMBDDService tdmBDDService) throws Exception {
		
		Response response = tdmBDDService.importFile();	
		Assert.assertEquals(response.getStatus(), HttpStatus.SC_OK);
		
	}

*/
	
}
