Feature: Test TDMBDD Import

Scenario: Import a valid file 

Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt'
And request 'Scenario Get all Event And CauseCode data By IMSI.xls'
When method POST
Then status 200
And match response == {"EventsLoaded": 16,"ErroneousEvents": 0}