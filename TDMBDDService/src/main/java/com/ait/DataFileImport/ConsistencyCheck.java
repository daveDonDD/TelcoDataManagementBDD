package com.ait.DataFileImport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ait.callData.BaseData;
import com.ait.callData.EventCause;
import com.ait.callData.FailureClass;
import com.ait.callData.MccMnc;
import com.ait.callData.UE;

public class ConsistencyCheck {
	
	public ErrorLog BaseDataConsistencyCheck(BaseData baseData,String workSheetName,long row,
			List<EventCause> eventCauseList,
			List<FailureClass> failureClassList,
			List<UE> UeClassList,
			List<MccMnc> mccMncList) {
		
		
		final ErrorLog errorLog;
		String errorMessage = null;
		
		if (!eventCauseConsistencyCheck(eventCauseList,baseData))
			errorMessage = "Event ID or Cause Code does not exist in the Event Cause Table";
		if (!failureClassConsistencyCheck(failureClassList,baseData))
			errorMessage = "Failure Class does not exist in the FailureClass Table";
		if (!UeTypeConsistencyCheck(UeClassList,baseData))
			errorMessage = "UE Type does not exist in UE Type Table";
		if (!MccMncConsistencyCheck(mccMncList,baseData))
			errorMessage = "UE Type does not exist in UE Type Table";
		
		if (errorMessage == null)
			return null;
		else errorLog = new ErrorLog(LocalDateTime.now(), workSheetName,row,errorMessage);
			return errorLog;
	}
	
	public boolean eventCauseConsistencyCheck(List<EventCause> eventCauseList,BaseData baseData) {
		// loop through every EventCause object in eventCauseList matching baseData.EventId 
		// create a sub list for matching event IDs
		// then check the corresponding cause code is is that sublist
		boolean result = false;
		List<EventCause> matchingEventIdList = new ArrayList<EventCause>();
		for(EventCause tempEC : eventCauseList) {
			if ( tempEC.getEvent_id() == baseData.getEvent_id() ) {
				matchingEventIdList.add(tempEC);
			}
			for(EventCause tempMatchingEventIdList : matchingEventIdList) {
				if (tempMatchingEventIdList.getCause_code() == baseData.getCause_code() )
					result = true;
			}
		}	
		return result;
	}	
	
	public boolean failureClassConsistencyCheck(List<FailureClass> failureClassList, BaseData baseData) {
		boolean result = false;
		for(FailureClass tempFc : failureClassList ){
			if( tempFc.getFailure_class() == baseData.getFailure_class())
				result = true;
		}		
		return result;
	}
	
	public boolean UeTypeConsistencyCheck(List<UE> UeClassList, BaseData baseData) {
		boolean result = false;
		for(UE tempFc : UeClassList ){
			if( tempFc.getTac() == baseData.getUe_type())
				result = true;
		}		
		return result;
	}

	public boolean MccMncConsistencyCheck(List<MccMnc> mccMncList, BaseData baseData) {
		boolean result = false;
		List<MccMnc> matchingMccMncList = new ArrayList<MccMnc>();
		for(MccMnc tempMM : mccMncList) {
			if ( tempMM.getMcc() == baseData.getMarket() ) {
				matchingMccMncList.add(tempMM);
			}
			for(MccMnc tempMatchingMarket : matchingMccMncList) {
				if (tempMatchingMarket.getMnc() == baseData.getOperator() )
					result = true;
			}
		}	
		return result;
	}
}
