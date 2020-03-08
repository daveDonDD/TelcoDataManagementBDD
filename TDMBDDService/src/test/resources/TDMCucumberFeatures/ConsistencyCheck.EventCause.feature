Feature: TDM Event Cause Consistency Check
	Validate Base Data against Event Cause reference tables

Background:
	Given the following eventCauseReferenceTable data
  |  cause_code | event_id | description   |
  |          0  |     4097 | Dummy text1   |
  |          1  |     4097 | Dummy text2   |
  |          0  |     4098 | Dummy text3   |
  |          2  |     4098 | Dummy text4   |


Scenario Outline: Event and Cause Code consistency check
	When check incoming event with <event_id> <cause_code>
	Then result should be <result>
	Examples:
		| event_id | cause_code  | result |
		|     4097 |          0  |   true |
		|     4099 |          0  |  false |
		|     4098 |         99  |  false |
		|     4098 |          0  |   true |
		|     4098 |        null |   true |
		|     4099 |        null |  false |
		|     4098 |        text |  false |
   
#Scenario: Event Validation Event-Cause data is consistent
#	When the Event ID and cause code are present in the EventCause table
#	Then the event is consistent
#
#Scenario: Event Validation Event-Cause data is inconsistent no Event ID
#	When the Event ID is not present in the EventCause table
#	Then the event is not consistent
#
#Scenario: Event-Cause data is inconsistent no Cause code matching event ID
#	When the cause code is not present in the EventCause table for that event ID
#	Then this event is not consistent
#
#Scenario Outline: event Cause data Consistency checking
#	Given Event and cause code data <Event> <CauseCode>
#	When checked against eventCAuseReferenceTable
#	Then the result is <result>
#	Examples:
#	|   Event  |  CauseCode |  result |
#	|    4097  |         0  |    true |
#   |    4099  |         1  |   false |
#   |    4097  |        99  |   false |


	 
# can add error counting later
#		And the error is logged
	