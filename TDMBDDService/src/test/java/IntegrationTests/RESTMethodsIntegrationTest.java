package IntegrationTests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
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

import com.ait.DAO.CallDataDAO;
import com.ait.DataFileImport.FileData;
import com.ait.TDMBDDService.TDMBDDService;
import com.ait.callData.BaseData;






//@RunWith(Arquillian.class)
public class RESTMethodsIntegrationTest {
//	@Deployment
//	public static Archive<?> createTestArchive() {
//		return ShrinkWrap
//				.create(JavaArchive.class, "CallDataTest.jar")
//				.addClasses(CallDataDAO.class, BaseData.class,TDMBDDService.class,FileData.class)
//				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
//				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//	}

	
/*8
	@EJB
	private CallDataDAO callDataDAO;
	
	
	@PersistenceContext
    private EntityManager em;
	
	private List<BaseData> baseDataList;
	
	private List<String> excelDataList;
	
	*/
	
	
/*
	@Before
	public void setUp() {
		callDataDAO.dropTables();
		
		baseDataList = new ArrayList<BaseData>();
		
		
		CallDataDAO callData = new CallDataDAO();
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098,	1, 21060800, 344, 930, 4, 1000, 0, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);
	
		baseDataList.add(baseData);
	
	}
*/
	/*
	@Test
	public void testTDMBSDDServiceHelloWorld() {
		TDMBDDService tdmBddService = new TDMBDDService();
		
		String result = tdmBddService.getTDMHelloWorld();
		assertEquals(result,"Hello TDMBDD World DDOY");
	}
	*/
	/*
	@Test
	public void testTDMBSDDServiceImportService() {
		TDMBDDService tdmBddService = new TDMBDDService();
		
		Response response = tdmBddService.importFile("src/test/resources/testData/TestDataset.xls");
		assertEquals(response.getStatus(), 200);
		}
	*/
}
