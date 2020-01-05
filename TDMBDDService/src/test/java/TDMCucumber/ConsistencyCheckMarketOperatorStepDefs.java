package TDMCucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ait.DataFileImport.ConsistencyCheck;
import com.ait.DataFileImport.FileData;
import com.ait.callData.BaseData;
import com.ait.callData.MccMnc;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class ConsistencyCheckMarketOperatorStepDefs {

private BaseData baseData;
private List<MccMnc> MccMncList;
private ConsistencyCheck consistencyCheck;

@Given("the following MccMncReferenceTable data")
public void the_following_MccMnc_data(DataTable dataTable) throws Throwable{
	MccMncList = dataTable.asList(MccMnc.class);
}


@When("MCC- MNC check incoming event with {int} {int}")
public void with(Integer int1, Integer int2) {
    baseData = new BaseData(LocalDateTime.now(), 4098,	1, 21060800, int1, int2, 4, 1000, 0, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);
}


@Then("MCC- MNC consistency result should be true")
public void MCC_MNC_consistency_result_should_be_true() {
	consistencyCheck = new ConsistencyCheck();
    assertTrue(consistencyCheck.MccMncConsistencyCheck(MccMncList, baseData));
}

@Then("MCC- MNC consistency result should be false")
public void MCC_MNC_consistency_result_should_be_false() {
	consistencyCheck = new ConsistencyCheck();
    assertFalse(consistencyCheck.MccMncConsistencyCheck(MccMncList, baseData));
}



}
