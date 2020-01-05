Feature: TDM Market Operator Consistency Check
	Validate Base Data against MCC - MNC reference table

Background:
	Given the following MccMncReferenceTable data
  |  Market | Operator | Country | OperatorName | 
  |     238 |        1 | Denmark |   TDC-DK     |
  |     238 |        2 | Denmark |  Sonofon     |
  |     310 |       10 |     USA |  Verison     |
  |     310 |       20 |     USA |     AT&T     |
  |     505	|       88 |     Aus | Localstar 	|
 

Scenario Outline: Market MCC and Operator MNC consistency check
	When MCC- MNC check incoming event with <market> <operator>
	Then MCC- MNC consistency result should be <result>
	Examples:
		| market | operator | result |
		|    238 |        1 |   true |
		|    238 |       99 |  false |
		|    310 |       10 |   true |
		|    310 |       20 |   true |
		|    999 |        1 |  false |
		
   

	