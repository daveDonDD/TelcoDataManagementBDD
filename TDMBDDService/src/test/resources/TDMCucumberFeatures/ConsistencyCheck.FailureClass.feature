Feature: TDM FailureClass Consistency Check
	Validate Base Data against Failure Class reference tables

Background:
	Given the following FailureClassReferenceTable data
  |  failure_class | description   |
  |              0 | Dummy text0   |
  |              1 | Dummy text1   |
 
 

Scenario Outline: Failure Class consistency check
	When check incoming event with failure Class set to <failure_class> 
	Then FC consistency check result should be <fc_result>
	Examples:
  |  failure_class  | fc_result   |
  |              0  | true        |
  |              1  | true        |
  |              2  | false       |
  |             99  | false       |
  |           null  | true        |
  |            text | false       |
 