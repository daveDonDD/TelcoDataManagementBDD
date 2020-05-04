package originalProjectTests;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.ait.Service.TDMBDDService;
import com.ait.DAO.CallDataDAO;


@RunWith(Arquillian.class)
public class CountIMSICallFailuresCustomerQueryIntegTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CallFailuresTest.jar")
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
	private DateTimeFormatter formatter;
	private String startDate;
	private String endDate;
	
	@Before
	public void setUp() {
		callDataDAO.dropTables();
		baseDataList = new ArrayList<BaseData>();
		baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000011L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		BaseData baseData2 = new BaseData(LocalDateTime.now(), 4099, "2", 21060803, 343, 932, 2, 1001, "2", "11B", 344930000000013L,
				4809532081614990001L, 8226896360947470001L, 1150444940909480001L);
		baseDataList.add(baseData2);
		BaseData baseData3 = new BaseData(LocalDateTime.now(), 4099, "2", 21060803, 343, 932, 2, 1001, "2", "11B", 344930000000011L,
				4809532081614990001L, 8226896360947470001L, 1150444940909480001L);
		baseDataList.add(baseData3);
		callDataDAO.addBaseData(baseDataList);

		formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		
		startDate = "2019/03/13T13:00:00";
		endDate = "2022/03/13T13:50:00";
	}


	@Test
	public void testCountCallFailuresPerImsiSuccess_CustomerQuery() {
		List<String> callFailuresCountCustomer= callDataDAO.countImsiFailuresForDuration(344930000000012L, startDate, endDate);
		assertEquals(callFailuresCountCustomer.size(), 0);
		
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098, "1", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);
		List<String> callFailuresCustomer= callDataDAO.countImsiFailuresForDuration(344930000000012L, startDate, endDate);
		assertEquals(callFailuresCustomer.size(), 1);
		
	}
	

	@Test
	public void testCountCallFailuresPerImsiInvalidDate_CustomerQuery() {
		List callFailuresCountCustomer = callDataDAO.countImsiFailuresForDuration(344930000000012L, startDate, endDate);
		assertEquals(callFailuresCountCustomer.size(), 0);

		BaseData baseData;
		LocalDateTime dateTimeOutsideRange = LocalDateTime.parse("11/09/2013 10:00:00", formatter);
		baseData = new BaseData(dateTimeOutsideRange, 44098, "2", 21060800, 344, 930, 4, 1000, "0", "11B", 344930000000012L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);
		
		//List<Object[]> callFailuresCountAfter = callDataDAO.countImsiFailures(344930000000012L,startDate, endDate);
		List<String> callFailuresCountAfter = callDataDAO.countImsiFailuresForDuration(344930000000012L, startDate, endDate);
		
		assertEquals(callFailuresCountAfter.size(), 0);
	}
}
