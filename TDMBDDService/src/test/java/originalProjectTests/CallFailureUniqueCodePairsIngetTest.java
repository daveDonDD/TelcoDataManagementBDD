package originalProjectTests;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
import org.junit.After;
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
public class CallFailureUniqueCodePairsIngetTest {

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

	private UE ueTable;
	private List<UE> ueDataList;
	private EventCause eventCauseTable;
	private List<EventCause> eventCauseDataList;
	
	@Before
	public void setUp() {
		callDataDAO.dropTables();
		baseDataList = new ArrayList<BaseData>();
		baseData = new BaseData(LocalDateTime.now(), 4106, "4", 100100, 440, 11, 3, 1011, "2", "13A", 191911000195930L,
				4809532081614990000L, 8226896360947470000L, 1150444940909480000L);
		baseDataList.add(baseData);
		baseData = new BaseData(LocalDateTime.now(), 4106, "2", 100100, 440, 11, 4, 1126, "2", "13A", 191911000284501L,
				6984274661809500160L, 2565772813028789760L, 5760424061083540480L);
		baseDataList.add(baseData);
		callDataDAO.addBaseData(baseDataList);

		ueDataList = new ArrayList<UE>();
		ueTable = new UE(100100, "G410", "Mitsubishi", "GSM 1800", "G410", "Mitsubishi", "null", "null", "null");
		ueDataList.add(ueTable);
		callDataDAO.addUE(ueDataList);

		eventCauseDataList = new ArrayList<EventCause>();
		eventCauseTable = new EventCause( "2", 4106, "INITIAL CTXT SETUP-FAILURE IN RADIO PROCEDURE");
		eventCauseDataList.add(eventCauseTable);
		callDataDAO.addEventCause(eventCauseDataList);
	}
	

	@Test
	public void test_call_failures_unique_code_pairs_tac_100100() {
		List response = callDataWS.countPhoneModelFailureDetails(100100);
//		List<List> responseLists = (List<List>) response.getEntity();
//		List intList = (List) response.get(0);
//		String eventId = "4106";
//		String causeCode = "2";
//		String pairCaunt = "2";
//		String description = "INITIAL CTXT SETUP-FAILURE IN RADIO PROCEDURE";
//		String makeModel = "Mitsubishi, G410";
		assertEquals(response.size(), 1);
//		assertEquals(eventId, intList.get(0));
//		assertEquals(causeCode, intList.get(1));
//		assertEquals(pairCaunt, intList.get(2));
//		assertEquals(description, intList.get(3));
//		assertEquals(makeModel, intList.get(4));
	}

}
