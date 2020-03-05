package com.ait.TDMBDDService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.DAO.CallDataDAO;

import com.ait.callData.*;

import com.ait.callData.BaseData;
import com.ait.DataFileImport.FileData;
import com.ait.callData.EventCause;
import com.ait.callData.PMData;


@Path("/TelcoDataMgt")
public class TDMBDDService {
	
		@EJB
		private CallDataDAO callDataDao;

		public TDMBDDService () {
		}
		
		public TDMBDDService(final CallDataDAO callDataDao) {
			this.callDataDao = callDataDao;
		}
		
	    @GET
	    @Produces(MediaType.TEXT_PLAIN)
	    @Path("/HelloWorldTest")
	    public String getTDMHelloWorld() {
	    	String hello = "Hello TDMBDD World DDOY + Queries 20";
	        return hello;
	    }
	  
		@POST	
		@Consumes(MediaType.TEXT_PLAIN)
		@Produces(MediaType.APPLICATION_JSON) //http return codes
		public Response importFile(String fileName) throws Exception{		
			final FileData fileData;
			final InputStream inputStream;
			
			try {
				inputStream = new FileInputStream("C:\\DevTools\\TDMBDD_Datafiles\\"+ fileName);
				//inputStream = new FileInputStream("C:\\DevTools\\TDMBDD_Datafiles\\TestDataSet.xls");
			} catch (Exception e) {
	
				/// Need to set to specific file not found exception1!!!!!!
					return Response.status(406).entity("File Not Found : " + fileName).build();
			}
			
			try {
				fileData = new FileData(inputStream);
			} catch (Exception e) {
				return Response.status(407).entity("BUG ").build();

			}
			
			FileData.excelDataList = new ArrayList<String>();
			FileData.rowNumber = 1;
		    try {
				fileData.importWorkbook();
				
			} catch (IOException e) {
				return Response.status(406).entity("Invalid File").build();
			}
		    callDataDao.addBaseData(fileData.getBaseDataList());
			callDataDao.addEventCause(fileData.getEventCauseList());
			callDataDao.addFailureClass(fileData.getFailureClassList());
			callDataDao.addUE(fileData.getUeList());
			callDataDao.addMccMnc(fileData.getMcc_mncList());
			
		    return Response.status(200).entity("FileLoaded : " + fileName).build();

		    
			// inputStream.close();    --   need to handle this and its exception

		}
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/{imsi}") 
		public List<EventCauseDTO>  getAllEventAndCauseCodeByImsi(@PathParam("imsi") final long imsi) {

			final List<Object[]> eventCauseDBList = callDataDao.getEventAndCauseCodeByIMSI(imsi);

			 
			List<EventCauseDTO> eventCauseDTOList = new ArrayList<>(eventCauseDBList.size());
			for ( Object[] eventCauseDB : eventCauseDBList) {
				eventCauseDTOList.add(new EventCauseDTO((int) eventCauseDB[0],
			                                      (int) eventCauseDB[1],
			                                      (String) eventCauseDB[2]));
			}
			return eventCauseDTOList;
		}
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/IMSIsWithinDates") 
		public List <ImsiWithinDatesDTO> getIMSIsWithinDates(@QueryParam("startDate") final String startDate,
				@QueryParam("endDate") final String endDate) {
			
			List imsiWithinDatesDBList = callDataDao.getImsisWithFailuresByDates(startDate,endDate);
			
			List<ImsiWithinDatesDTO> imsiWithinDatesDTOList = new ArrayList<>(imsiWithinDatesDBList.size());
//			for ( imsiWithinDatesDB : imsiWithinDatesDBList) {
			for ( int i = 0 ; i < imsiWithinDatesDBList.size( ) ; i++) {	
				imsiWithinDatesDTOList.add(new ImsiWithinDatesDTO((long)imsiWithinDatesDBList.get(i)));
				}
			return imsiWithinDatesDTOList;
			}
		
		
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/CallFailureCountByPhoneModel") // User Story #6
		public Response CallFailureCountByPhoneModel(@QueryParam("ueType") final Integer ueType,
				@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate) {
			final List<String> callFailuresCount = callDataDao.countImsiFailuresForUEType(ueType, startDate, endDate);
			return Response.status(200).entity(callFailuresCount).build();
		}

	
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/CountOfIMSIFailureAndDuration") 
		public List<CountImsiFailureDurationDTO> CountOfIMSIFailureAndDuration(@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate) {

			List<Object[]> callFailuresCountDBList = callDataDao.countImsiFailuresDuration(startDate,endDate);
			
			List<CountImsiFailureDurationDTO> countImsiFailureDurationDTOList = new ArrayList<>(callFailuresCountDBList.size());
			for ( Object[] callFailuresCountDB : callFailuresCountDBList) {
				countImsiFailureDurationDTOList.add(new CountImsiFailureDurationDTO((long) callFailuresCountDB[0],
			                                      									(long) callFailuresCountDB[1],
			                                      									(long) callFailuresCountDB[2]));
				}
				return countImsiFailureDurationDTOList;
		}
		
		

		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/CountPhoneModelFailureDetails/{model}") 
		public List<CountPhoneModelFailuresDTO> countPhoneModelFailureDetails(@PathParam("model") final Integer model) {
//			final List<String> callFailuresCount = callDataDao.countPhoneModelFailures(ueType);

			List<Object[]> countPhoneModelFailuresDBList = callDataDao.countPhoneModelFailures(model);
			
			List<CountPhoneModelFailuresDTO> countPhoneModelFailuresDTOList = new ArrayList<>(countPhoneModelFailuresDBList.size());
			for ( Object[] countPhoneModelFailuresDB : countPhoneModelFailuresDBList) {
				countPhoneModelFailuresDTOList.add(new CountPhoneModelFailuresDTO((int)countPhoneModelFailuresDB[0],
			                                      									(int)countPhoneModelFailuresDB[1],
			                                      									(int)countPhoneModelFailuresDB[2],
			                                      									(long)countPhoneModelFailuresDB[3],
			                                       									(String)countPhoneModelFailuresDB[4]));
				}
				return countPhoneModelFailuresDTOList;		
			}
}
