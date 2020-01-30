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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.apache.poi.EncryptedDocumentException;

import java.lang.String;

import com.ait.DAO.CallDataDAO;
import com.ait.DataFileImport.FileData;

import com.ait.callData.EventCause;


@Path("/TelcoDataMgt")
public class TDMBDDService {
	
		@EJB
		private CallDataDAO callDataDao;


	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/HelloWorldTest")
	    public String getTDMHelloWorld() {
	    	String hello = "Hello TDMBDD World DDOY + Queries 7";
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
		@Path("/{imsi}") // User Story #4
		public Response getAllEventAndCauseCodeByImsi(@PathParam("imsi") final long imsi) {
			final List<EventCause> imsiEventList = callDataDao.getEventAndCauseCodeByIMSI(imsi);
			return Response.status(200).entity(imsiEventList).build();
		}
		
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		@Path("/callFailureCount") // User Story #9
		public Response countIMSICallFailuresAndDuration(@QueryParam("imsi") final Long imsi,
				@QueryParam("startDate") final String startDate, @QueryParam("endDate") final String endDate) {
			final List<Object[]> callFailuresCount = callDataDao.countImsiFailures(imsi, startDate, endDate);
			return Response.status(200).entity(callFailuresCount).build();
		}
		
}
