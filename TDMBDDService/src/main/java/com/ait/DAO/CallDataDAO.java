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

import com.ait.TDMBDDService.EventCauseDTO;
import com.ait.TDMBDDService.ImsiWithinDatesDTO;
import com.ait.callData.*;


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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addEventCause(final List<EventCause> eventCauseList) {
		for (final EventCause eventCause : eventCauseList) {
			// entityManager.merge(eventCause);
			final Query query = entityManager.createNativeQuery(
					"INSERT INTO EventCause(cause_code,event_id,description) VALUES(:causecode,:eventid,:description) ON DUPLICATE KEY UPDATE cause_code=:causecode, event_id=:eventid, description=:description");
			query.setParameter("causecode", eventCause.getCause_code());
			query.setParameter("eventid", eventCause.getEvent_id());
			query.setParameter("description", eventCause.getDescription());
			query.executeUpdate();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addFailureClass(final List<FailureClass> failureClassList) {
		for (final FailureClass failureClass : failureClassList) {
			entityManager.merge(failureClass);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addUE(final List<UE> ueList) {
		for (final UE ue : ueList) {
			entityManager.merge(ue);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addMccMnc(final List<MccMnc> mcc_mncList) {
		for (final MccMnc mcc_mnc : mcc_mncList) {
			entityManager.merge(mcc_mnc);
		}
	}

	public void dropTables() {
		entityManager.createQuery("DELETE FROM BaseData").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE BaseData AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createQuery("DELETE FROM EventCause").executeUpdate();
		entityManager.createQuery("DELETE FROM FailureClass").executeUpdate();
		entityManager.createQuery("DELETE FROM MccMnc").executeUpdate();
		entityManager.createQuery("DELETE FROM UE").executeUpdate();	
	}


	
	public List getEventAndCauseCodeByIMSI(final long imsi) {
		final String select = "select eventcause.cause_code,eventcause.event_id , eventcause.description from basedata\r\n"
				+ "left join eventcause on eventcause.event_id=basedata.event_id and \r\n"
				+ "eventcause.cause_code=basedata.cause_code where basedata.imsi=:imsi";
		final Query query = entityManager.createNativeQuery(select);
		query.setParameter("imsi", imsi);
		return query.getResultList();
	}
	
	public List getImsisWithFailuresByDates(final String startDate, final String endDate) {
		final Query query = entityManager.createQuery("SELECT DISTINCT imsi FROM BaseData w Where w.date_time between ?1 and ?2");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		return query.getResultList();
	}
	

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

	
	public List countImsiFailuresDuration(final String startDate, final String endDate) {
		final Query query = entityManager.createQuery(
				"SELECT imsi, COUNT(*), SUM(duration) from BaseData b WHERE date_time BETWEEN ?1 and ?2 GROUP BY imsi");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		return query.getResultList();
	}
	
	public List countPhoneModelFailures(final Integer ueType) {
		final Query query = entityManager.createQuery(
				"SELECT b.ue_type, b.event_id,  b.cause_code, count( b.cause_code), e.description FROM BaseData b left join UE u on b.ue_type = u.tac join EventCause e on e.event_id=b.event_id and e.cause_code=b.cause_code where u.tac = ?1 group by b.event_id,b.cause_code");
		query.setParameter(1, ueType);
		return query.getResultList();
	}
		
	public List<String> countImsiFailuresForDuration(final Long imsi, final String startDate, final String endDate) {
		final Query query = entityManager.createQuery(
				"SELECT COUNT(*) from BaseData b WHERE date_time BETWEEN ?1 and ?2 AND imsi=?3 GROUP BY imsi");
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
	
	
	public List Top10MarketOpCellCombo(final String startDate, final String endDate) {
		final Query query = entityManager.createQuery("SELECT m.country, m.operator, b.cell_id, count(b.event_id) FROM BaseData b, MccMnc m\r\n"
				+ "WHERE b.market = m.mcc\r\n"
				+ "AND b.operator = m.mnc\r\n"
				+ "AND b.date_time BETWEEN ?1 AND ?2\r\n"
				+ "GROUP BY b.market, b.operator, b.cell_id\r\n"
				+ "ORDER BY Count(b.event_id) DESC");
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final String startDateReplaceT = startDate.replace('T', ' ');
		final String endDateReplaceT = endDate.replace('T', ' ');
		final LocalDateTime startDateTime = LocalDateTime.parse(startDateReplaceT, formatter);
		final LocalDateTime endDateTime = LocalDateTime.parse(endDateReplaceT, formatter);
		query.setParameter(1, startDateTime);
		query.setParameter(2, endDateTime);
		return query.getResultList();
	}
	
	
	
		
		/*!!!!!!!!!!!!!!!!!!!!!!!!!1
	public List<String> getModelNameForUEType(final Integer ueType) {
		final Query query = entityManager
				.createQuery("SELECT u.manufacturer, u.marketing_name FROM UE u WHERE u.tac = ?1");
		query.setParameter(1, ueType);
		return query.getResultList();
	}

	// User Story #10
countPhoneModelFailures	public List<List<String>> getCallFailureUniqueCodePairsAndCountPerTac(final Integer ueType) {
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
