package unitTestsBDDCucumber;

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
import com.ait.callData.EventCause;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class ConsistencyCheckEventCauseStepDefs {

private BaseData baseData;
private List<EventCause> eventCauseList;
private ConsistencyCheck consistencyCheck;

@Given("the following eventCauseReferenceTable data")
public void the_following_eventCause_data(DataTable dataTable) throws Throwable{
	eventCauseList = dataTable.asList(EventCause.class);
}


@When("check incoming event with {int} {int}")
public void with(Integer int1, Integer int2) {
    baseData = new BaseData(LocalDateTime.now(), int1,	1, 21060800, 344, 930, 4, 1000, int2, "11B", 344930000000011L, 4809532081614990000L, 8226896360947470000L,	1150444940909480000L);
}


@Then("result should be true")
public void result_should_be_true() {
	consistencyCheck = new ConsistencyCheck();
    assertTrue(consistencyCheck.eventCauseConsistencyCheck(eventCauseList, baseData));
}

@Then("result should be false")
public void result_should_be_false() {
	consistencyCheck = new ConsistencyCheck();
    assertFalse(consistencyCheck.eventCauseConsistencyCheck(eventCauseList, baseData));
}


}
