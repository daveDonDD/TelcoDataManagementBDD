package TDMCucumber;

import static org.junit.Assert.assertEquals;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ait.DataFileImport.FileData;
import com.ait.callData.EventCause;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ImportStepDefs {

	private FileData fileData;
	
	@Given("A testdata file BaseData Table for import")
	public void a_testdata_file_for_import() throws FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/testData/TestDataset.xls");
		fileData = new FileData(inputStream);
		FileData.excelDataList = new ArrayList<String>();
		FileData.rowNumber = 1;
	}

	@When("I initiate the import")
	public void i_initiate_the_import() throws IOException {
	    fileData.importWorkbook();
	}

	@Then("All data is loaded into the system")
	public void all_data_is_loaded_into_the_system() {
		assertEquals(fileData.getBaseDataList().size(),2);    // only 2 events match short event cause list.
	}


	@Then("All errors are logged")
	public void all_errors_are_logged() {
		assertEquals(fileData.getErrorLogList().size(),4);
	}


	@Given("A testdata file with valid reference tables")
	public void a_testdata_file_with_valid_reference_tables() throws FileNotFoundException {
		InputStream inputStream = new FileInputStream("src/test/resources/testData/TestDataset.xls");
		fileData = new FileData(inputStream);
		FileData.excelDataList = new ArrayList<String>();
		FileData.rowNumber = 1;
	}	

	@Then("All reference tables are loaded into the system")
	public void all_reference_tables_are_loaded_into_the_system() {
		assertEquals(fileData.getEventCauseList().size(),9);
		assertEquals(fileData.getFailureClassList().size(),5);
		assertEquals(fileData.getUeList().size(),9);
		assertEquals(fileData.getMcc_mncList().size(),41);	    
	}
	


}
	

