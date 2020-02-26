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

import org.modelmapper.ModelMapper;

import com.ait.DAO.CallDataDAO;
import com.ait.DataFileImport.FileData;
import com.ait.callData.BaseData;
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
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/PMObjectTest1")
	    public List<PMData> getPMMessage1() {
	        List<PMData> data = new ArrayList<>();
	        data.add(new PMData("MSC"));
	        data.add(new PMData("ERBS"));
	        data.add(new PMData("RadioNode"));
	        return data;
	    }
	 
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/{imsi}") // User Story #4
		public List<EventCauseDTO>  getAllEventAndCauseCodeByImsi(@PathParam("imsi") final long imsi) {

			List<Object[]> eventCauseDBList = callDataDao.getEventAndCauseCodeByIMSI(imsi);

			 
			List<EventCauseDTO> eventCauseDTOList = new ArrayList<>(eventCauseDBList.size());
			for ( Object[] eventCauseDB : eventCauseDBList) {
				eventCauseDTOList.add(new EventCauseDTO((int) eventCauseDB[0],
			                                      (int) eventCauseDB[1],
			                                      (String) eventCauseDB[2]));
			}
			return eventCauseDTOList;
//			return Response.status(200).entity(eventCauseObjectList).build();
		}
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/2/{imsi}") // User Story #4
		public List<EventCause> get_2_AllEventAndCauseCodeByImsi(@PathParam("imsi") final long imsi) {

			List<Object[]> eventCauseDBList = callDataDao.getEventAndCauseCodeByIMSI(imsi);

			 
			List<EventCause> eventCauseList = new ArrayList<>(eventCauseDBList.size());
			for ( Object[] eventCauseDB : eventCauseDBList) {
				eventCauseList.add(new EventCause((int) eventCauseDB[0],
			                                      (int) eventCauseDB[1],
			                                      (String) eventCauseDB[2]));
			}
			return eventCauseList;
//			return Response.status(200).entity(eventCauseObjectList).build();
		}
		
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/getIMSIsWithinDates") // User Story #5
		public List <BaseData> getIMSIsWithinDates(@QueryParam("startDate") final String startDate,
				@QueryParam("endDate") final String endDate) {
			final long startTime = System.currentTimeMillis();
			final List <BaseData> baseTable = callDataDao.getImsisWithFailuresByDates(startDate, endDate);
			
			
			final long elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("\"findAllIMSIsWithFailuresByTime\" Elapsed Time: " + elapsedTime / 1000.0 + "sec");
//			return Response.status(200).entity(baseTable).build();
			return baseTable;
		}
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/CallFailureCountByPhoneModel") // User Story #6
		public Response CallFailureCountByPhoneModel(@QueryParam("ueType") final Integer ueType,
				@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate) {
			final List<String> callFailuresCount = callDataDao.countImsiFailuresForUEType(ueType, startDate, endDate);
			return Response.status(200).entity(callFailuresCount).build();
		}

		// US7
		// comments on returned data
		// This query was never mapped to an object so was always going to be a list......
		// unless I created a new DTO and mapped the resulting query to that DTO
		// worth doing to make Client side easier - else they just manage the list - which is reasonable
		// depends what solution is agreed and documented in the API.
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/CountOfIMSIFailureAndDuration") // User Story #7
		public Response CountOfIMSIFailureAndDuration(@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate) {
			final List<Object[]> callFailuresCountDBList = callDataDao.countImsiFailures(startDate, endDate);
			return Response.status(200).entity(callFailuresCountDBList).build();
		}
}
