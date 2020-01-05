package TDMBDD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.DataFileImport.FileData;
import com.ait.callData.CallDataDAO;
import java.lang.String;

@Path("/TelcoDataMgt")
public class TDMBDDService {
	
		@EJB
		private CallDataDAO callDataDao;


	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @Path("/HelloWorldTest")
	    public String getTDMHelloWorld() {
	    	String hello = "Hello TDMBDD World DDOY";
	        return hello;
	    }


		@POST	
		@Consumes(MediaType.TEXT_PLAIN)
		@Produces(MediaType.APPLICATION_JSON) //http return codes
		public Response importFile(String fileName){		
			final FileData fileData;
			final InputStream inputStream;
			
			try {
				inputStream = new FileInputStream("C:\\DevTools\\TDMBDD_Datafiles\\"+ fileName);
			} catch (Exception e) {
					return Response.status(406).entity("File Not Found : " + fileName).build();

			}
			fileData = new FileData(inputStream);
			FileData.excelDataList = new ArrayList<String>();
			FileData.rowNumber = 1;
		    try {
				fileData.importWorkbook();
			} catch (IOException e) {
				return Response.status(406).entity("Invalid File").build();
			}
			callDataDao.addBaseData(fileData.getBaseDataList());
		    return Response.status(200).entity("FileLoaded : " + fileName).build();
		
		}

}
