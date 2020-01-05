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

import com.ait.callData.BaseData;
import com.ait.callData.CallDataDAO;






@RunWith(Arquillian.class)
public class CallDataDaoIntegrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "CallDataTest.jar")
				.addClasses(CallDataDAO.class, BaseData.class)
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	

	@EJB
	private CallDataDAO callDataDAO;
	
	
	@PersistenceContext
    private EntityManager em;
	
	private List<BaseData> baseDataList;
	
	private List<String> excelDataList;

	@Before
	public void setUp() {
		callDataDAO.dropTables();
		
		baseDataList = new ArrayList<BaseData>();
		
		
		CallDataDAO callData = new CallDataDAO();
		BaseData baseData = new BaseData(LocalDateTime.now(), 4098,	1, 21060800, 344, 930, 4, 1000, 0, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);
	
		baseDataList.add(baseData);
	
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

}
