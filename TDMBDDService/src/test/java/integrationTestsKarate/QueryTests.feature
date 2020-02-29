Feature: Test TDMBDD queries

Scenario: Get all Event And CauseCode data By IMSI
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/344930000000012'
When method GET
Then status 200
And match response == [{"cause_code":23,"event_id":4125,"description":"UE CTXT RELEASE-UNKNOWN OR ALREADY ALLOCATED MME UE S1AP ID"},{"cause_code":11,"event_id":4106,"description":"INITIAL CTXT SETUP-TRANSPORT REJECT"}]


Scenario: Get Call Failure Count By Phone Model
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/CallFailureCountByPhoneModel?ueType=21060800&startDate=2020/01/11T17:14:00&endDate=2020/11/01T17:20:00'
When method GET
Then status 200
And match response == [3]

Scenario: Count of IMSI Failure And Duration of those failures per IMSI
Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt/CountOfIMSIFailureAndDuration?startDate=2020/01/23T14:00:00&endDate=2020/01/23T14:07:00'
When method GET
Then status 200
And match response == [{"imsi":777770000000011,"failureCount":1,"duration":1000},{"imsi":777770000000012,"failureCount":2,"duration":2000},{"imsi":777770000000013,"failureCount":3,"duration":3000},{"imsi":777770000000014,"failureCount":1,"duration":1000}]

