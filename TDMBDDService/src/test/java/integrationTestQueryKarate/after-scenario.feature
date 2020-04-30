@ignore
Feature: Database cleaning n an 'afterScenario' hook


Scenario: clean the database before each scenario begins

* print 'in "DB_Cleaning after-scenario.feature", caller:', caller

#Create JDBC connection with DbUtils java class

* def config = { username: 'root', password: 'LoughTalt12', url: 'jdbc:mysql://localhost:3306/global_database', driverClassName: 'com.mysql.jdbc.Driver' }
* def DbUtils = Java.type('util.DbUtils')
* def db = new DbUtils(config)

* db.cleanDatatable("TRUNCATE global_database.basedata;")