package integrationTestsArquillian;

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
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.DAO.CallDataDAO;
import com.ait.callData.*;



 


@RunWith(Arquillian.class)
public class CallDataDaoIntegrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "CallDataTest.jar")
				.addClasses(CallDataDAO.class )
				.addPackage(BaseData.class.getPackage())
				.addAsManifestResource("META-INF/test_persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	

	@EJB
	private CallDataDAO callDataDAO;
	
	
	@PersistenceContext
    private EntityManager em;
	
	private List<BaseData> baseDataList;
	private List<EventCause> eventCauseList;
	private List<FailureClass> failureClassList;
	private List<UE> ueList;
	private List<MccMnc> mcc_mncList;
	
	private List<String> excelDataList;

	@Before
	public void setUp() {
		callDataDAO.dropTables();
		
		baseDataList = new ArrayList<BaseData>();
		eventCauseList = new ArrayList<EventCause>();
		failureClassList = new ArrayList<FailureClass>();
		ueList = new ArrayList<UE>();
		mcc_mncList = new ArrayList<MccMnc>();
				
		
		CallDataDAO callData = new CallDataDAO();
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098,	1, 21060800, 344, 930, 4, 1000, 0, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);
		EventCause eventCause = new EventCause(5941, 32154, "RRC CONN SETUP-SUCCESS2");
		FailureClass failureClass = new FailureClass(10, "TestFailure");
		UE ue = new UE(100123,"G410","Mitsubishi","GSM 1800, GSM 900","G410","Mitsubishi","Handheld","Blackberry","Qwerty");
		MccMnc mcc_Mnc = new MccMnc(30,34,"United States of America","Wireless Solutions International US ");
			
		baseDataList.add(baseData);
		eventCauseList.add(eventCause);
		failureClassList.add(failureClass);
		ueList.add(ue);
		mcc_mncList.add(mcc_Mnc);
	}

	
	
	@Test
	public void addBaseDataTest() {
		Query query = em.createQuery("SELECT b FROM BaseData b");	
		int size = query.getResultList().size();
		callDataDAO.addBaseData(baseDataList);
		query = em.createQuery("SELECT b FROM BaseData b");
		assertEquals(query.getResultList().size(),size+1);
	}

	@Test
		// Purpose was just to verify DB connecting and testing = done, could remove this test later
	public void checkEmptyTable() {
		Query query = em.createQuery("SELECT b FROM BaseData b");	
		int size = query.getResultList().size();
		assertEquals(0,size);
	}
	

	@Test
	public void addEventCauseTest() {
		Query query = em.createQuery("SELECT b FROM EventCause b");	
		int size = query.getResultList().size();
		callDataDAO.addEventCause(eventCauseList);
		query = em.createQuery("SELECT b FROM EventCause b");
		assertEquals(query.getResultList().size(),size+1);
	}
	
	@Test
	public void addFailureClass() {
		Query query = em.createQuery("SELECT b FROM FailureClass b");	
		int size = query.getResultList().size();
		callDataDAO.addFailureClass(failureClassList);
		query = em.createQuery("SELECT b FROM FailureClass b");
		assertEquals(query.getResultList().size(),size+1);
	}

	@Test
	public void addUE() {
		Query query = em.createQuery("SELECT b FROM UE b");	
		int size = query.getResultList().size();
		callDataDAO.addUE(ueList);
		query = em.createQuery("SELECT b FROM UE b");
		assertEquals(query.getResultList().size(),size+1);
	}

	@Test
	public void addMccMnc() {
		Query query = em.createQuery("SELECT b FROM MccMnc b");	
		int size = query.getResultList().size();
		callDataDAO.addMccMnc(mcc_mncList);
		query = em.createQuery("SELECT b FROM MccMnc b");
		assertEquals(query.getResultList().size(),size+1);
	}
	
	
	
}
