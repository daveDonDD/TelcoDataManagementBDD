package com.ait.DataFileImport;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ait.callData.BaseData;
import com.ait.callData.EventCause;
import com.ait.callData.FailureClass;
import com.ait.callData.MccMnc;
import com.ait.callData.UE;


public class FileData {
	private Workbook workbook;
	public static List<String> excelDataList;
	private List<BaseData> baseDataList;
	private List<EventCause> eventCauseList;
	private List<FailureClass> failureClassList;
	private List<UE> ueList;
	private List<MccMnc> mccMncList;
	private List<ErrorLog> errorLogList;
	private DateTimeFormatter formatter;
	public static String worksheetName;
	public static long rowNumber;
	private ConsistencyCheck consistencyCheck;

	public FileData() {
	}
 
	public FileData(final InputStream fileInputStream) throws Exception{
		try {
			workbook = WorkbookFactory.create(fileInputStream);
		} catch ( EncryptedDocumentException | InvalidFormatException |IOException e) {
			throw new IllegalStateException(e);
		} 
		excelDataList = new ArrayList<String>();
		baseDataList = new ArrayList<BaseData>();
		eventCauseList = new ArrayList<EventCause>();
		failureClassList = new ArrayList<FailureClass>();
		ueList = new ArrayList<UE>();
		mccMncList = new ArrayList<MccMnc>();
		errorLogList = new ArrayList<ErrorLog>();
		
		consistencyCheck = new ConsistencyCheck();

		
		formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		rowNumber = 0;
		worksheetName = "";
	}

	public boolean importWorkbook() throws IOException {
		if (workbook.getNumberOfSheets() != 5 || workbook.getSheetAt(0).getRow(0).getLastCellNum() != 14) {
			return false;
		}
		for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
			importSheets(i);
		}
		importSheets(0);// base data
		return true;
	}

	/*
	 * Loops through each cell in each row of the excel document and adds it to a
	 * list An object of the relevant type is created from this list and the list is
	 * cleared
	 */
	public void importSheets(final int sheetNumber) throws IOException {
		worksheetName = workbook.getSheetName(sheetNumber);
		final Sheet spreadsheet = workbook.getSheetAt(sheetNumber);
		for (int i = 1; i <= spreadsheet.getLastRowNum(); i++) {
			final Row row = spreadsheet.getRow(i);
			for (final Cell cell : row) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {
					final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					excelDataList.add(dateFormat.format(cell.getDateCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					excelDataList.add(Double.toString(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING) {
					if (cell.toString().equals("(null)")) {
						excelDataList.add(null);
					} else {
						excelDataList.add(cell.toString());
					}
				}
			}
			rowNumber = i;
			this.addObjectToList(sheetNumber);
		}
		workbook.close();
	}

	/*
	 * Adds an object to the relevant object list depending on the sheet number
	 * @param number of the excel sheet of the object
	 */
	public void addObjectToList(final int sheetNumber) {
		
		final ErrorLog errorLog;
		
		switch (sheetNumber) {
		case 0:
			final LocalDateTime dateTime = LocalDateTime.parse(excelDataList.get(0), formatter);

			final BaseData baseData = new BaseData.Builder().date_time(dateTime)
					.event_id((int) Double.parseDouble(excelDataList.get(1)))
					.failure_class((int) Double.parseDouble(excelDataList.get(2)))
					.ue_type((int) Double.parseDouble(excelDataList.get(3)))
					.market((int) Double.parseDouble(excelDataList.get(4)))
					.operator((int) Double.parseDouble(excelDataList.get(5)))
					.cell_id((int) Double.parseDouble(excelDataList.get(6)))
					.duration((int) Double.parseDouble(excelDataList.get(7)))
					.cause_code((int) Double.parseDouble(excelDataList.get(8))).ne_version(excelDataList.get(9))
					.imsi((long) Double.parseDouble(excelDataList.get(10)))
					.hier3_id((long) Double.parseDouble(excelDataList.get(11)))
					.hier32_id((long) Double.parseDouble(excelDataList.get(12)))
					.hier321_id((long) Double.parseDouble(excelDataList.get(13))).build();
			
			errorLog = consistencyCheck.BaseDataConsistencyCheck(baseData, worksheetName, rowNumber, eventCauseList, failureClassList, ueList, mccMncList);
			if (errorLog == null)
				baseDataList.add(baseData);
			else 
				errorLogList.add(errorLog);
			 
					
			break;
	
	 	case 1:
	 
	//		if (!tableFilter.isEventCauseValid(excelDataList)) {
	//			break;
	//		}
			final EventCause eventCause = new EventCause((int) Double.parseDouble(excelDataList.get(0)),
	  						(int) Double.parseDouble(excelDataList.get(1)), excelDataList.get(2));
			eventCauseList.add(eventCause);
			break;
		case 2:
	//		if (!tableFilter.isFailureClassValid(excelDataList)) {
	//			break;
	//		}
			final FailureClass failureClass = new FailureClass((int) Double.parseDouble(excelDataList.get(0)),
					excelDataList.get(1));
			failureClassList.add(failureClass);
			break;
		case 3:
	//		if (!tableFilter.isUEValid(excelDataList)) {
	//			break;
	//		}
			final UE ue = new UE((int) Double.parseDouble(excelDataList.get(0)), excelDataList.get(1),
					excelDataList.get(2), excelDataList.get(3), excelDataList.get(4), excelDataList.get(5),
					excelDataList.get(6), excelDataList.get(7), excelDataList.get(8));
			ueList.add(ue);
			break;
		case 4:
	//		if (!tableFilter.isMCC_MNCValid(excelDataList)) {
	//			break;
	//		}

			final MccMnc mcc_mnc = new MccMnc((int) Double.parseDouble(excelDataList.get(0)),
					(int) Double.parseDouble(excelDataList.get(1)), excelDataList.get(2), excelDataList.get(3));
			mccMncList.add(mcc_mnc);
			break;
		}    
		excelDataList.clear();
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public List<BaseData> getBaseDataList() {
		return baseDataList;
	}

	public List<EventCause> getEventCauseList() {
		return eventCauseList;
	}

	public List<FailureClass> getFailureClassList() {
		return failureClassList;
	}

	public List<UE> getUeList() {
		return ueList;
	}

	public List<MccMnc> getMcc_mncList() {
		return mccMncList;
	}
	
	public List<ErrorLog> getErrorLogList() {
		return errorLogList;
	}
	
	
/*
	public TableFilter getTableFilter() {
		return tableFilter;
	}
*/
}
