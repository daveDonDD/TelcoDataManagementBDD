package originalProjectTests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.callData.BaseData;
import com.ait.callData.EventCause;
import com.ait.callData.FailureClass;
import com.ait.callData.MccMnc;
import com.ait.callData.UE;
import com.ait.DataFileImport.ErrorLog;
import com.ait.DataFileImport.FileData;
import com.ait.DAO.CallDataDAO;
import com.ait.TDMBDDService.TDMBDDService;

@RunWith(Arquillian.class)
public class CountNumberCallFailuresModelIntegTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CallCountTest.jar")
				.addClasses(CallDataDAO.class, BaseData.class, EventCause.class, FailureClass.class, MccMnc.class,
						UE.class, FileData.class, ErrorLog.class)
				.addPackage(TDMBDDService.class.getPackage())
				.addAsManifestResource("META-INF/test_persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	private CallDataDAO callDataDAO;

	@EJB
	private TDMBDDService callDataWS;

	@PersistenceContext
	private EntityManager em;
	private BaseData baseData;
	private List<BaseData> baseDataList;
	private List<UE> ueDataList;
	private DateTimeFormatter formatter;
	private String startDate;
	private String endDate;
	
	@Before
	public void setUp() {
		callDataDAO.dropTables();
		baseDataList = new ArrayList<BaseData>();
		ueDataList = new ArrayList<UE>();
		formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		
		startDate = "2019/03/13T13:00:00";
		endDate = "2022/03/13T13:50:00";
	}

	
	@Test
	public void testCountCallFailuresModelSuccess() {
//		List<Object[]> callFailuresCount= callDataDAO.countImsiFailures(344930000000012L, startDate, endDate);
//		assertEquals(callFailuresCount.size(), 0);
		
		List<String> callFailuresCount2= callDataDAO.countImsiFailuresForUEType(21060800, startDate, endDate);
		assertEquals(callFailuresCount2.get(0), 0L);
		
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);
		List<String> callFailures= callDataDAO.countImsiFailuresForUEType(21060800, startDate, endDate);
		assertEquals(callFailures.get(0), 1L);
		
	}

	@Test
	public void testCountCallFailuresPerModelInvalidDate() {
		List<String> callFailuresCount2= callDataDAO.countImsiFailuresForUEType(21060800, startDate, endDate);
		assertEquals(callFailuresCount2.get(0), 0L);

		BaseData baseData;
		LocalDateTime dateTimeOutsideRange = LocalDateTime.parse("11/09/2013 10:00:00", formatter);
		baseData = new BaseData(dateTimeOutsideRange, 44098, "2", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);
		
		List<String> callFailures= callDataDAO.countImsiFailuresForUEType(21060800, startDate, endDate);
		assertEquals(callFailures.get(0), 0L);
	}
	
	@Test
	public void testCountCallFailuresModelSuccessWS() {
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);
		Response response=callDataWS.CallFailureCountByPhoneModel(21060800, startDate, endDate);
		List<String> ueList = (List<String>) response.getEntity();	
		assertEquals(ueList.get(0),1L);
	}
	
	@Test
	public void findModelForUETypeWS() {
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		
		UE ue = new UE(21060800,"VEA3","S.A.R.L. B  & B International","GSM 1800, GSM 900","VEA3","S.A.R.L. B  & B International",null,null,null);
		ueDataList.add(ue);
		callDataDAO.addUE(ueDataList);
		
		
		List response=callDataWS.countPhoneModelFailureDetails(21060800);
//		List<String> ueList2 = (List<String>) response.getEntity();	
		//String ue2 = (String) response.getEntity();	
		assertEquals(response.size(),0);
	}
	
/*	DAO test - no value
	@Test
	public void testGetModelNameForUEType() {
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, 0, "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		
		UE ue = new UE(21060800,"VEA3","S.A.R.L. B  & B International","GSM 1800, GSM 900","VEA3","S.A.R.L. B  & B International",null,null,null);
		ueDataList.add(ue);
		callDataDAO.addUE(ueDataList);
		
		
		List<String> model =callDataDAO.getModelNameForUEType(21060800);
		
		assertEquals(model.size(),1);
	}
	*/
}
