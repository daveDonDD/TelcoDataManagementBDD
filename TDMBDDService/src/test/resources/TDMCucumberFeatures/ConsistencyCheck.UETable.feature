Feature: TDM UETable Consistency Check
	Validate Base Data against UETable reference tables

Background:
	Given the following UEType_in_ReferenceTable data
  |   ue_type | 
  |    100100 | 
  |  21060800 |
  |  33000953 |
 

Scenario Outline: UE Type  consistency check
	When check incoming event with Ue Type set to <ue_type> 
	Then UE Type consistency check result should be <ue_result>
	Examples:
  |  ue_type       | ue_result   |
  |              0 | false       |
  |              1 | false       |
  |              2 | false       |
  |       21060800 | true        |
  |         100100 | true        |
  |       99999999 | false       |