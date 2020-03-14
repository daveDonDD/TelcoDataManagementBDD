
CREATE SCHEMA if not exists `global_database` ;
use global_database;
--
-- Table structure for table `BaseData`
--

DROP TABLE IF EXISTS `BaseData`;
CREATE TABLE `BaseData` (
  `date_time` datetime NOT NULL,
  `event_id` int(11) NOT NULL,
  `failure_class` varchar(10) NOT NULL,
  `ue_type` double NOT NULL,
  `market` int(11) NOT NULL,
  `operator` int(11) NOT NULL,
  `cell_id` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `cause_code` int(11) NOT NULL,
  `ne_version` varchar(10) NOT NULL,
  `imsi` bigint NOT NULL,
  `hier3_id` bigint NOT NULL,
  `hier32_id` bigint NOT NULL,
  `hier321_id` bigint NOT NULL,
  PRIMARY KEY (`date_time`,`failure_class`,`ue_type`,`market`,`operator`,`cell_id`,`duration`,`cause_code`,`event_id`,`ne_version`,`imsi`,`hier3_id`,`hier32_id`,`hier321_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Table structure for table `EventCause`
--

DROP TABLE IF EXISTS `EventCause`;
CREATE TABLE `EventCause` (
  `cause_code` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`cause_code`,`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `FailureClass`
--

DROP TABLE IF EXISTS `FailureClass`;
CREATE TABLE `FailureClass` (
  `failure_class` int(11) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`failure_class`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `MccMnc`
--

DROP TABLE IF EXISTS `MccMnc`;
CREATE TABLE `MccMnc` (
  `mcc` int(11) NOT NULL,
  `mnc` int(11) NOT NULL,
  `country` varchar(100) DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`mcc`,`mnc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `UE`
--

DROP TABLE IF EXISTS `UE`;
CREATE TABLE `UE` (
  `tac` double NOT NULL,
  `marketing_name` varchar(100) DEFAULT NULL,
  `manufacturer` varchar(100) DEFAULT NULL,
  `access_capability` varchar(200) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `vendor_name` varchar(100) DEFAULT NULL,
  `ue_type` varchar(45) DEFAULT NULL,
  `os` varchar(45) DEFAULT NULL,
  `input_mode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
	userName VARCHAR(45)PRIMARY KEY,
	userPassword VARCHAR(45),
    userType VARCHAR(45)
  );
  
INSERT INTO user VALUES( 'John', 'rooT963', 'systAdmin');
INSERT INTO user VALUES( 'Ann', 'P086tre', 'netMngEng');
INSERT INTO user VALUES( 'Adam', 'cusT56', 'custServRep');  
INSERT INTO user VALUES( 'Eric', '1xyzSup', 'supEng');
INSERT INTO user VALUES( 'Swen', 'ANetw003', 'netMngEng');  


  
  --
-- Table structure for table `ErrorLog`
--
 DROP TABLE IF EXISTS `ErrorLog`;
 CREATE TABLE `ErrorLog` (
 `errorLog_ID` int NOT NULL AUTO_INCREMENT,
 `date_time` datetime NOT NULL,
 `table_name` VARCHAR(50) DEFAULT NULL,
 `row` bigint DEFAULT NULL,
 `description` VARCHAR(1000) DEFAULT NULL,
   PRIMARY KEY (`errorLog_ID`));
  
       --
-- Table structure for table `FullErrorLog`
--
 DROP TABLE IF EXISTS `FullErrorLog`;
 CREATE TABLE `FullErrorLog` (
 `errorLog_ID` int NOT NULL AUTO_INCREMENT,
 `date_time` datetime NOT NULL,
 `table_name` VARCHAR(50) DEFAULT NULL,
 `row` bigint DEFAULT NULL,
 `description` VARCHAR(1000) DEFAULT NULL,
   PRIMARY KEY (`errorLog_ID`));
   
DROP TRIGGER IF EXISTS  errorlog_insert;
DELIMITER //
CREATE TRIGGER errorlog_insert 
BEFORE INSERT ON `errorlog`
FOR EACH ROW
BEGIN
	INSERT INTO FullErrorLog VALUES(NEW.errorLog_ID, NEW.date_time, NEW.table_name, NEW.row, NEW.description);
END//
