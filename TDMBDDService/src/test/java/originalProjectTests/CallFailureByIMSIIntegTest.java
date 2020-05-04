package originalProjectTests;

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
import com.ait.Service.CountImsiFailureDurationDTO;
import com.ait.Service.CountPhoneModelFailuresDTO;
import com.ait.Service.EventCauseDTO;
import com.ait.Service.ImsiWithinDatesDTO;
import com.ait.Service.TDMBDDService;
import com.ait.DAO.CallDataDAO;

@RunWith(Arquillian.class)
public class CallFailureByIMSIIntegTest {
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CallDataTest.jar")
				.addClasses(CallDataDAO.class, BaseData.class, EventCause.class, FailureClass.class, MccMnc.class,
						UE.class, FileData.class, ErrorLog.class)
				.addClasses(EventCauseDTO.class)
				.addClasses(CountPhoneModelFailuresDTO.class)
				.addClasses(CountImsiFailureDurationDTO.class)
				.addClasses(ImsiWithinDatesDTO.class)
				.addClasses(TDMBDDService.class)				
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
		baseData = new BaseData(LocalDateTime.now(), 4098, "1", 100900, 344, 930, 4, 1000, "13", "11B", 344930000000011L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);		
		baseDataList.add(baseData);
		
		eventCauseList = new ArrayList<EventCause>();
		eventCause = new EventCause("13",4098,"desc");
		eventCauseList.add(eventCause);
		
		callDataDAO.addBaseData(baseDataList);
		callDataDAO.addEventCause(eventCauseList);

 
		imsi = baseData.getImsi();
		startDate = "2013-02-19";
		endDate = "2020-02-20";
		
//		callDataWS = new TDMBDDService(callDataDAO);

	}

	
	@Test
	public void testFindEventCauseForImsi() {
		List<EventCause> eventCauseList = callDataDAO.getEventAndCauseCodeByIMSI(imsi);
		assertEquals(1,eventCauseList.size());
	}
	
	@Test
	public void testGetImsisWithFailuresByDates() {
		String startDate2 = "2019/03/13T13:00:00";
		String endDate2 = "2022/03/13T13:50:00";
		List<BaseData> imsiBaseDataList = callDataDAO.getImsisWithFailuresByDates(startDate2, endDate2);
		assertEquals(1, imsiBaseDataList.size());
	}
	
	@Test
	public void testFindAllByImsi() {
		List<EventCauseDTO> eventCauseDTOList = callDataWS.getAllEventAndCauseCodeByImsi(imsi);
		assertEquals(1,eventCauseDTOList.size());
	}
	
	@Test
	public void testFindAllIMSIsWithFailuresByTime() {
		List list = callDataWS.getIMSIsWithinDates("2019/03/13T13:00:00","2022/03/13T13:50:00");
		assertEquals( 1,list.size());
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
