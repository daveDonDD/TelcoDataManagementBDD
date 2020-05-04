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

import org.apache.http.HttpStatus;
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
import com.ait.DataFileImport.ConsistencyCheck;
import com.ait.DAO.CallDataDAO;

@RunWith(Arquillian.class)
public class ImportIntegTest {
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CallDataTest.jar")
				.addClasses(CallDataDAO.class, BaseData.class, EventCause.class, FailureClass.class, MccMnc.class,
						UE.class, FileData.class, ErrorLog.class,ConsistencyCheck.class)
				.addClasses(EventCauseDTO.class)
				.addClasses(CountPhoneModelFailuresDTO.class)
				.addClasses(CountImsiFailureDurationDTO.class)
				.addClasses(ImsiWithinDatesDTO.class)
				.addClasses(TDMBDDService.class)	
				.addPackages(true, "org.apache.poi")
				.addAsManifestResource("META-INF/test_persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	private CallDataDAO callDataDAO;

	@EJB
	private TDMBDDService callDataWS;

	@PersistenceContext
	private EntityManager em;
	
	

	@Before
	public void setUp() {
		callDataDAO.dropTables();
	}

	
	@Test
	public void testImport() throws Exception{
		Response response = callDataWS.importFile("QueryTestDataSet.xls");
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		}
	


}
