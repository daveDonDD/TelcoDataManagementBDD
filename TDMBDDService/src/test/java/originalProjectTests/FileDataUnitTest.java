package originalProjectTests;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import com.ait.DataFileImport.FileData;

public class FileDataUnitTest {
	FileData fileData;

	@Before
	public void setUp() throws Exception  {
		InputStream inputStream = new FileInputStream("src/test/resources/testData/UnitTestDataset.xls");
		fileData = new FileData(inputStream);
		FileData.excelDataList = new ArrayList<String>();
		FileData.rowNumber = 1;
//		fileData.getTableFilter().getEventIDList().add("1234");
//		fileData.getTableFilter().getFailureClassList().add("1");
//		fileData.getTableFilter().getTacList().add("12345678");
//		fileData.getTableFilter().getCauseCodeList().add("1");
	}

//	@Test
//	public void fileDataConstructorTest() throws Exception {
//		fileData = new FileData();
//		InputStream inputStream = new FileInputStream("src/test/resources/testData/calldatatest.txt");
//		fileData = new FileData(inputStream);
//		assertEquals(fileData.getWorkbook().getSheetName(0),"Base Data");
//	}
	
	@Test
	public void importWorkbookTest() throws Exception {
		fileData.importWorkbook();
		assertEquals(fileData.getBaseDataList().size(),0);
		assertEquals(fileData.getEventCauseList().size(),7);
		assertEquals(fileData.getFailureClassList().size(),5);
		assertEquals(fileData.getMcc_mncList().size(),11);
		assertEquals(fileData.getUeList().size(),9);
	}
	
//	@Test(expected = IllegalStateException.class)
//	public void testInvalidFileFormat() throws Exception {
//		InputStream inputStream = new FileInputStream("src/test/resources/testData/calldatatest.txt");
//		fileData = new FileData(inputStream);
//	}
	
	@Test
	public void fileData_addObjectToList_validationFails_BaseData(){
		FileData.excelDataList.clear();
		FileData.excelDataList.add("02/16/2019 17:15:00");
		FileData.excelDataList.add("1234");
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("12345678");
		FileData.excelDataList.add("123");
		FileData.excelDataList.add("123");
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("1234");
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("11B");
		FileData.excelDataList.add("344930000000011");
		FileData.excelDataList.add("4809532081614990000");
		FileData.excelDataList.add("8226896360947470000");
		FileData.excelDataList.add("1150444940909480000");
		fileData.addObjectToList(0);
		assertEquals(FileData.excelDataList.size(),0);
	}
	
	@Test
	public void fileData_addObjectToList_validationFails_EventCause(){
		FileData.excelDataList.clear();
		FileData.excelDataList.add("a");
		FileData.excelDataList.add("1234");
		FileData.excelDataList.add("RRC CONN SETUP-SUCCESS");
		fileData.addObjectToList(1);
		assertEquals(FileData.excelDataList.size(),0);
	}
	
	@Test
	public void fileData_addObjectToList_validationFails_FailureClass(){
		FileData.excelDataList.clear();
		FileData.excelDataList.add("a");
		FileData.excelDataList.add("EMERGENCY");
		fileData.addObjectToList(2);
		assertEquals(FileData.excelDataList.size(),0);
	}
	
	@Test
	public void fileData_addObjectToList_validationFails_UE(){
		FileData.excelDataList.clear();
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("G410");
		FileData.excelDataList.add("Mitsubishi");
		FileData.excelDataList.add("GSM 1800, GSM 900");
		FileData.excelDataList.add("G410");
		FileData.excelDataList.add("Mitsubishi");
		FileData.excelDataList.add("HANDHELD");
		FileData.excelDataList.add("OS");
		FileData.excelDataList.add("BASIC");
		fileData.addObjectToList(3);
		assertEquals(FileData.excelDataList.size(),0);
	}
	
	@Test
	public void fileData_addObjectToList_validationFails_MCC_MNC(){
		FileData.excelDataList.clear();
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("1");
		FileData.excelDataList.add("Denmark");
		FileData.excelDataList.add("TDC-DK");
		fileData.addObjectToList(4);
		assertEquals(FileData.excelDataList.size(),0);
	}
	
	/*@Test(expected = IllegalStateException.class)
	public void testWorkbookIO() throws Exception {
		Workbook workbook = mock(Workbook.class);
		doThrow(IOException.class).when(workbook).close();
		InputStream inputStream = new FileInputStream("TestDataset.xls");
		//doReturn(workbook).when(WorkbookFactory.class).create(inputStream);
		//when(WorkbookFactory.create(inputStream)).thenReturn(workbook);
		fileData = new FileData(inputStream);
		fileData.importWorkbook();
	}*/
}
