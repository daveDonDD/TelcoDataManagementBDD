package TDMCucumber;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ait.DataFileImport.FileData;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefs {

	private FileData fileData;
	
	@Given("A testdata file for import")
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
		assertEquals(fileData.getBaseDataList().size(),6);

	}
	
}
