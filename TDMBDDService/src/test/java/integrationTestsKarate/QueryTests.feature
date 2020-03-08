Feature: Test TDMBDD queries

Scenario: Get all Event And CauseCode data By IMSI
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/344930000000012'
When method GET
Then status 200
And match response == [{"cause_code":23,"event_id":4125,"description":"UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED MME UE S1AP ID"},{"cause_code":11,"event_id":4106,"description":"INITIAL CTXT SETUP-TRANSPORT REJECT"}]


Scenario: Get all IMSIs with failures during a time period
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/IMSIsWithinDates?startDate=2020/01/11T15:00:00&endDate=2020/01/11T21:20:00'
When method GET
Then status 200
And match response == [{"imsi": 344930000000011},{"imsi": 344930000000012},{"imsi": 310560000000013},{"imsi": 344930000000014}]
	
Scenario: Get call failure count by phone model
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/CallFailureCountByPhoneModel?ueType=21060800&startDate=2020/01/11T17:14:00&endDate=2020/11/01T17:20:00'
When method GET
Then status 200
And match response == [3]

Scenario: Get a count of IMSI Failure And Duration of those failures for all IMSIs during a time period
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/CountOfIMSIFailureAndDuration?startDate=2020/01/23T14:00:00&endDate=2020/01/23T14:07:00'
When method GET
Then status 200
And match response == [{"imsi":777770000000011,"failureCount":1,"duration":1000},{"imsi":777770000000012,"failureCount":2,"duration":2000},{"imsi":777770000000013,"failureCount":3,"duration":3000},{"imsi":777770000000014,"failureCount":1,"duration":1000}]


Scenario: Get all the unique failure Event Id and Cause Code combinations for a given model of phone
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/CountPhoneModelFailureDetails/33002635'
When method GET
Then status 200
And match response == [{"phoneModel":33002635,"eventId":4125,"causeCode":23,"count":7,"eventDescription":"UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED MME UE S1AP ID"},{"phoneModel":33002635,"eventId":4097,"causeCode":9,"count":1,"eventDescription":"RRC CONN SETUP-S1 INTERFACE DOWN"},{"phoneModel":33002635,"eventId":4125,"causeCode":5,"count":1,"eventDescription":"UE CTXT RELEASE-CS FALLBACK TRIGGERED"}]

Scenario: Get call failure count by IMSI
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/countImsiFailuresForDuration?imsi=777770000000014&startDate=2020/01/11T19:16:00&endDate=2020/03/04T19:20:00'
When method GET
Then status 200
And match response == [2]


Scenario: Get Top 10 Market/Operator/Cell ID combinations for a duration
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/Top10MarketOpCellCombo?startDate=2020/01/01T19:16:00&endDate=2020/04/04T19:20:00'
When method GET
Then status 200

