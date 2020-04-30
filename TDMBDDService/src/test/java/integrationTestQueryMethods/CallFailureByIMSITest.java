package integrationTestQueryMethods;

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
import org.jboss.shrinkwrap.api.spec.WebArchive;
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
public class CallFailureByIMSITest {
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CallDataTest.jar")
				.addClasses(CallDataDAO.class, BaseData.class, EventCause.class, FailureClass.class, MccMnc.class,
						UE.class, FileData.class, ErrorLog.class)
				.addPackage(TDMBDDService.class.getPackage())
				.addAsManifestResource("META-INF/test_persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
//		 return ShrinkWrap.create(WebArchive.class,"RestMethodInteg.war")
//				  .addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
//				  .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//				  .addPackage(FileData.class.getPackage())
//				  .addPackage(BaseData.class.getPackage())
//				  .addPackage(TDMBDDService.class.getPackage())
//				  .addPackages(true, "org.apache.poi")
//				  .addClasses(CallDataDAO.class)
//			      .setWebXML("test_web.xml");   
	}

	@EJB
	private CallDataDAO callDataDAO;

//	@EJB
	private TDMBDDService callDataWS;

	@PersistenceContext
	private EntityManager em;
	
	private BaseData baseData;
	private EventCause eventCause;
	private List<BaseData> baseDataList;
	private List<EventCause> eventCauseList;

	private long imsi;
	private String startDate;
	private String endDate;

	@Before
	public void setUp() {
		
		
		callDataDAO.dropTables();
		
		baseDataList = new ArrayList<BaseData>();
		baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000011L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);		
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);

		
		eventCauseList = new ArrayList<EventCause>();
		eventCause = new EventCause("1",4098,"des");
		eventCauseList.add(eventCause);
		callDataDAO.addEventCause(eventCauseList);
	 
		imsi = baseData.getImsi();
		startDate = "2013-02-19";
		endDate = "2020-02-20";
		
		callDataWS = new TDMBDDService(callDataDAO);

	}

	
	@Test
	public void testFindEventCauseForImsi() {
		List<EventCause> eventCause = callDataDAO.getEventAndCauseCodeByIMSI(imsi);
		assertEquals(eventCause.size(), 1);
	}
	
	@Test
	public void testGetImsisWithFailuresByDates() {
		String startDate2 = "2019/03/13T13:00:00";
		String endDate2 = "2022/03/13T13:50:00";
		List<BaseData> imsiBaseDataList = callDataDAO.getImsisWithFailuresByDates(startDate2, endDate2);
		assertEquals(imsiBaseDataList.size(), 1);
	}
	
	@Test
	public void testFindAllByImsi() {
		List list = callDataWS.getAllEventAndCauseCodeByImsi(imsi);
		assertEquals(list.size(), 1);
	}
	
	@Test
	public void testFindAllIMSIsWithFailuresByTime() {
		List list = callDataWS.getIMSIsWithinDates("2019/03/13T13:00:00","2022/03/13T13:50:00");
		assertEquals(list.size(), 1);
	}
	
//	@Test
//	public void testGetDataByImsi() {
//		List<BaseData> imsiBaseDataList = callDataDAO.getDataByImsi();
//		assertEquals(imsiBaseDataList.size(), 1);
//	}
//	
//	@Test
//	public void testGetImsisWithFailuresByIMSIandDates() {
//		List<BaseData> imsiBaseDataList = callDataDAO.getImsisWithFailuresByIMSIandDates(imsi, startDate);
//		assertEquals(imsiBaseDataList.size(), 1);
//	}

}
