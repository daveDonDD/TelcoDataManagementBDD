Feature: Test TDMBDD Import

Scenario: Import a valid file 

Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt'
And request 'QueryTestDataSet.xls'
When method POST
Then status 200
And match response == {"EventsLoaded": 16,"ErroneousEvents": 0}

Scenario: Import : file not found 

Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt'
And request 'nonExistentFile.xls'
When method POST
Then status 406
And match response == 'Import failed : File Not Found'

Scenario: Import invalid file type

Given url 'http://localhost:8080/DDOY-build/rest/TelcoDataMgt'
And request 'QueryTestDataSet_invalidFileFormat.xls'
When method POST
Then status 407
And match response == 'Import failed : Invalid File Format'