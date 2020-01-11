package com.ait.DAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.callData.BaseData;

@Stateless
@LocalBean
public class CallDataDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public CallDataDAO() {
	}

	public CallDataDAO(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addBaseData(final List<BaseData> baseDataList) {
		for (final BaseData baseData : baseDataList) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			final String formatDateTime = baseData.getDate_time().format(formatter);
			// resultList.add(entityManager.merge(baseData));
			final Query query = entityManager.createNativeQuery(
					"INSERT INTO BaseData(date_time,event_id,failure_class,ue_type,market,operator,cell_id,duration,cause_code,ne_version,imsi,hier3_id,hier32_id,hier321_id) VALUES(:date_time,:eventid,:failure_class,:ue_type,:market,:operator,:cell_id,:duration,:cause_code,:ne_version,:imsi,:hier3_id,:hier32_id,:hier321_id) ON DUPLICATE KEY UPDATE date_time=:date_time, event_id=:eventid, failure_class=:failure_class, ue_type=:ue_type, market=:market, operator=:operator, cell_id=:cell_id, duration=:duration, cause_code=:cause_code, ne_version=:ne_version, imsi=:imsi, hier3_id=:hier3_id, hier32_id=:hier32_id, hier321_id=:hier321_id");
			query.setParameter("date_time", formatDateTime);
			query.setParameter("eventid", baseData.getEvent_id());
			query.setParameter("failure_class", baseData.getFailure_class());
			query.setParameter("ue_type", baseData.getUe_type());
			query.setParameter("market", baseData.getMarket());
			query.setParameter("operator", baseData.getOperator());
			query.setParameter("cell_id", baseData.getCell_id());
			query.setParameter("duration", baseData.getDuration());
			query.setParameter("cause_code", baseData.getCause_code());
			query.setParameter("ne_version", baseData.getNe_version());
			query.setParameter("imsi", baseData.getImsi());
			query.setParameter("hier3_id", baseData.getHier3_id());
			query.setParameter("hier32_id", baseData.getHier32_id());
			query.setParameter("hier321_id", baseData.getHier321_id());
			query.executeUpdate();
		}
	}

	

	public void dropTables() {
		entityManager.createQuery("DELETE FROM BaseData").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE BaseData AUTO_INCREMENT = 1").executeUpdate();
		
	}

/*
 * NOTE:  DAO query tests for all queries here too - 
 * 			as per test analysis - this is duplicate of oterh types of unit and integration tests.
 * 
 * 
	// User Story #4
	public List<EventCause> getAllByIMSI(final long imsi) {
		final String select = "select eventcause.cause_code,eventcause.event_id , eventcause.description from basedata\r\n"
				+ "left join eventcause on eventcause.event_id=basedata.event_id and \r\n"
				+ "eventcause.cause_code=basedata.cause_code where basedata.imsi=:imsi";
		final Query query = entityManager.createNativeQuery(select);
		query.setParameter("imsi", imsi);
		return query.getResultList();
	}

	// User Story #7
	public List<BaseData> getImsisWithFailuresByDates(final String startDate, final String endDate) {
		final Query query = entityManager.createQuery("SELECT w.imsi, w.date_time  FROM BaseData w Where w.date_time between ?1 and ?2");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		final List<BaseData> toDisplay = query.getResultList();
		return toDisplay;
	}

	// User Story #9
	public List<Object[]> countImsiFailures(final Long imsi, final String startDate, final String endDate) {
		final Query query = entityManager.createQuery(
				"SELECT imsi, COUNT(*), SUM(duration) from BaseData b WHERE date_time BETWEEN ?1 and ?2 AND imsi=?3 GROUP BY imsi");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		query.setParameter(3, imsi);
		return query.getResultList();
	}

	// User Story #8
	public List<String> countImsiFailuresForUEType(final Integer ueType, final String startDate, final String endDate) {
		final Query query = entityManager
				.createQuery("SELECT COUNT(*) from BaseData b WHERE date_time BETWEEN ?1 and ?2 AND ue_type = ?3");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		query.setParameter(3, ueType);
		return query.getResultList();
	}

	public List<String> getModelNameForUEType(final Integer ueType) {
		final Query query = entityManager
				.createQuery("SELECT u.manufacturer, u.marketing_name FROM UE u WHERE u.tac = ?1");
		query.setParameter(1, ueType);
		return query.getResultList();
	}

	// User Story #10
	public List<List<String>> getCallFailureUniqueCodePairsAndCountPerTac(final Integer ueType) {
		final String queryString = "SELECT b.event_id,  b.cause_code, count( b.cause_code),  e.description,  CONCAT(u.manufacturer, \", \", u.model) As \"Make and Model\" \n"
				+ " FROM basedata b left join ue u on b.ue_type = u.tac join eventcause e on e.event_id=b.event_id and \n"
				+ "e.cause_code=b.cause_code where u.tac = ?1 group by b.event_id,b.cause_code;";
		final Query query = entityManager.createNativeQuery(queryString);
		query.setParameter(1, ueType);
		final List<List<String>> result = new ArrayList<>();
		final List resultList = query.getResultList();
		for (final Object obj : resultList) {
			final Object[] objArray = (Object[]) obj;
			final List<String> stringList = new ArrayList<>();
			for (int i = 0; i < objArray.length; i++) {
				stringList.add(String.valueOf(objArray[i]));
			}
			result.add(stringList);
		}
		return result;
	}

	// User Story #13
	public List<Object[]> countImsiFailuresForCustomerSer(final Long imsi, final String startDate,
			final String endDate) {
		final Query query = entityManager.createQuery(
				"SELECT imsi, COUNT(*) from BaseData b WHERE date_time BETWEEN ?1 and ?2 AND imsi=?3 GROUP BY imsi");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		query.setParameter(3, imsi);
		return query.getResultList();
	}

	public List<String> getTacNumber() {
		final Query query = entityManager
				.createQuery("SELECT u.tac, CONCAT(u.manufacturer, ', ', u.model) FROM UE u order by u.manufacturer");
		return query.getResultList();
	}
*/
}
