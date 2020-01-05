Feature: TDM Import
	Import excelfile into application
	
Scenario: Import BaseData worksheet from Valid file 
	Given A testdata file BaseData Table for import
	When I initiate the import
	Then All data is loaded into the system
	
Scenario: Import reference Tables from a Valid file
	Given A testdata file with valid reference tables
	When I initiate the import
	Then All reference tables are loaded into the system

# first steps def just calls filedata class
# how would we integration cover the rest POST call to import 
# junit test would not have jaxrs annotations instantiated.----need Arquillian + add DB behind that.
 
# Question for test heads
# suggestion do one jaxrs plumbing test - a full import

#proposed improvement - 
# 	Given A testdata file with 6 valid rows for import
#	When I initiate the import
#	Then All 6 rows are loaded into the system

# but ultimately need to look at better example tests
# error cases should be easy to write - can could implement the error handling via TDD is do it bottom up.

 
	