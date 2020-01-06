package com.ait.DataFileImport;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;



// Persistence to be set up later - unit testing the object for now
//It will fail DB functionality until I set up the tabel in teh DB - and have test capability for that
//@Entity
//@XmlRootElement
public class ErrorLog {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long errorLog_ID;
	private LocalDateTime date_time;
	private String table_name;
	private long row;
	private String description;

	public ErrorLog() {

	}

	public ErrorLog(final LocalDateTime date_time, final String table_name, final long row,
			final String description) {
		this.date_time = date_time;
		this.table_name = table_name;
		this.row = row;
		this.description = description;
	}

//	public void logError(String tableName, long row, String errorMessage) {
//		errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
//		ErrorLog errorLog = new ErrorLog(LocalDateTime.now(), FileData.worksheetName,
//				FileData.rowNumber + 1, errorMessage);
//		errorLogList.add(errorLog);
//		errorMessage = "";
//	}
	public Long getErrorLog_ID() {
		return errorLog_ID;
	}

	public void setErrorLog_ID(final Long errorLog_ID) {
		this.errorLog_ID = errorLog_ID;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(final LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public String getTableName() {
		return table_name;
	}

	public void setTableName(final String table_name) {
		this.table_name = table_name;
	}

	public long getRow() {
		return row;
	}

	public void setRow(final long row) {
		this.row = row;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
