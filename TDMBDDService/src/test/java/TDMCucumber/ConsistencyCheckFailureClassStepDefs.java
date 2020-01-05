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
import com.ait.callData.FailureClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class ConsistencyCheckFailureClassStepDefs {

private BaseData baseData;
private List<FailureClass> failureClassList;
private ConsistencyCheck consistencyCheck;


@Given("the following FailureClassReferenceTable data")
public void the_following_FailureClassReferenceTable_data(DataTable dataTable) {
	failureClassList = dataTable.asList(FailureClass.class);
}

@When("check incoming event with failure Class set to {int}")
public void check_incoming_event_with_failure_Class_set_to(Integer int1) {
    baseData = new BaseData(LocalDateTime.now(), 4097,	int1, 21060800, 344, 930, 4, 1000, 0, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);

}


@Then("FC consistency check result should be true")
public void fc_consistency_check_result_should_be_true() {
	consistencyCheck = new ConsistencyCheck();
    assertTrue(consistencyCheck.failureClassConsistencyCheck(failureClassList, baseData));
}

@Then("FC consistency check result should be false")
public void fc_consistency_check_result_should_be_false() {
	consistencyCheck = new ConsistencyCheck();
    assertFalse(consistencyCheck.failureClassConsistencyCheck(failureClassList, baseData));
}
}