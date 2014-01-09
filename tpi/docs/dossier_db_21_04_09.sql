-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.41-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema dossier
--

CREATE DATABASE IF NOT EXISTS dossier;
USE dossier;

--
-- Definition of table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `ADDRESS_ID` int(10) unsigned NOT NULL auto_increment,
  `TYPE` smallint(5) unsigned NOT NULL,
  `PRIMARY` char(1) NOT NULL,
  `COUNTRY` char(3) NOT NULL,
  `ADDRESS1` varchar(45) NOT NULL,
  `ADDRESS2` varchar(45) default NULL,
  `CITY` varchar(60) NOT NULL,
  `STATE` char(2) default NULL,
  `ZIP` varchar(9) default NULL,
  `LONGITUDE` double default NULL,
  `LATITUDE` double default NULL,
  `RESIDENT_ID` int(10) unsigned NOT NULL,
  `RESIDENT_TYPE` smallint(5) unsigned NOT NULL,
  PRIMARY KEY  (`ADDRESS_ID`),
  KEY `RESIDENT_INDEX` (`RESIDENT_ID`,`RESIDENT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `address`
--

/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;


--
-- Definition of table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `COMPANY_ID` int(10) unsigned NOT NULL auto_increment,
  `NAME` varchar(60) NOT NULL,
  `PHONE` varchar(16) default NULL,
  `FAX` varchar(16) default NULL,
  `TYPE` char(1) default NULL,
  `SIZE` char(1) default NULL,
  `BUSINESS_TYPE` char(1) default NULL,
  PRIMARY KEY  (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`COMPANY_ID`,`NAME`,`PHONE`,`FAX`,`TYPE`,`SIZE`,`BUSINESS_TYPE`) VALUES 
 (5,'Company C',NULL,NULL,NULL,NULL,NULL),
 (6,'XYZ Company',NULL,NULL,NULL,NULL,NULL),
 (7,'Baker\'s Bank',NULL,NULL,NULL,NULL,NULL),
 (8,'TRL Complete',NULL,NULL,NULL,NULL,NULL),
 (9,'ABC Company',NULL,NULL,NULL,NULL,NULL),
 (10,'US Trust Bank',NULL,NULL,NULL,NULL,NULL),
 (11,'Credit UK',NULL,NULL,NULL,NULL,NULL),
 (12,'Metrobank',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


--
-- Definition of table `faq_list`
--

DROP TABLE IF EXISTS `faq_list`;
CREATE TABLE `faq_list` (
  `FAQ_ID` int(10) unsigned NOT NULL auto_increment,
  `OBJECT_ID` int(10) unsigned NOT NULL,
  `QUESTION` varchar(255) default NULL,
  `ANSWER` varchar(255) default NULL,
  `ORDER_NO` smallint(5) unsigned default NULL,
  PRIMARY KEY  (`FAQ_ID`),
  KEY `Index_2` (`OBJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `faq_list`
--

/*!40000 ALTER TABLE `faq_list` DISABLE KEYS */;
INSERT INTO `faq_list` (`FAQ_ID`,`OBJECT_ID`,`QUESTION`,`ANSWER`,`ORDER_NO`) VALUES 
 (50019,39018,NULL,NULL,0),
 (80523,50522,NULL,NULL,0),
 (82524,81523,NULL,NULL,0),
 (83027,83026,NULL,NULL,0),
 (103534,83533,NULL,NULL,0),
 (104036,104035,NULL,NULL,0),
 (104538,104537,NULL,NULL,0),
 (105040,105039,NULL,NULL,0),
 (105546,105543,NULL,NULL,0),
 (106047,106044,NULL,NULL,0),
 (107048,106545,NULL,NULL,0),
 (107558,107557,NULL,NULL,0),
 (108578,108577,NULL,NULL,0),
 (110100,109099,NULL,NULL,0),
 (110701,110700,NULL,NULL,0),
 (111205,111204,NULL,NULL,0),
 (118414,118411,NULL,NULL,0),
 (118921,118912,'Why do we start this project?','We have to improve performance of something',0),
 (121918,119917,NULL,NULL,0);
/*!40000 ALTER TABLE `faq_list` ENABLE KEYS */;


--
-- Definition of table `file_lib`
--

DROP TABLE IF EXISTS `file_lib`;
CREATE TABLE `file_lib` (
  `FILE_ID` int(10) unsigned NOT NULL auto_increment,
  `OBJECT_ID` varchar(36) NOT NULL,
  `CONTENT` blob NOT NULL,
  `FILE_NAME` varchar(45) default NULL,
  `TYPE` char(1) default NULL,
  PRIMARY KEY  (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `file_lib`
--

/*!40000 ALTER TABLE `file_lib` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_lib` ENABLE KEYS */;


--
-- Definition of table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `PERSON_ID` int(10) unsigned NOT NULL auto_increment,
  `CONTACT_EMAIL` varchar(45) NOT NULL,
  `FIRST_NAME` varchar(20) NOT NULL,
  `LAST_NAME` varchar(20) NOT NULL,
  `CREATED_AT` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `UPDATED_AT` timestamp NOT NULL default '0000-00-00 00:00:00',
  `DATE_OF_BIRTH` datetime default NULL,
  `PASSWORD` char(32) default NULL,
  `LOCKED` char(1) default NULL,
  `STATUS` smallint(5) unsigned default NULL,
  `WORK_EMAIL` varchar(45) default NULL,
  `SECONDARY_EMAIL` varchar(45) default NULL,
  `AIM` varchar(45) default NULL,
  `SKYPE` varchar(45) default NULL,
  `GOOGLE_TALK` varchar(45) default NULL,
  `WINDOWS_LIVE` varchar(45) default NULL,
  `YAHOO` varchar(45) default NULL,
  `HOME_PHONE_NUMBER` varchar(16) default NULL,
  `MOBILE_PHONE_NUMBER` varchar(16) default NULL,
  `BUSINESS_PHONE_NUMBER` varchar(16) default NULL,
  `FAX_NUMBER` varchar(16) default NULL,
  `PAGER` varchar(16) default NULL,
  PRIMARY KEY  (`PERSON_ID`),
  UNIQUE KEY `Index_2` (`CONTACT_EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `person`
--

/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` (`PERSON_ID`,`CONTACT_EMAIL`,`FIRST_NAME`,`LAST_NAME`,`CREATED_AT`,`UPDATED_AT`,`DATE_OF_BIRTH`,`PASSWORD`,`LOCKED`,`STATUS`,`WORK_EMAIL`,`SECONDARY_EMAIL`,`AIM`,`SKYPE`,`GOOGLE_TALK`,`WINDOWS_LIVE`,`YAHOO`,`HOME_PHONE_NUMBER`,`MOBILE_PHONE_NUMBER`,`BUSINESS_PHONE_NUMBER`,`FAX_NUMBER`,`PAGER`) VALUES 
 (595,'wei.ling@metrobank.com','Wei ','Ling','2009-04-18 21:47:25','2009-03-26 06:40:27','1972-05-23 00:00:00',NULL,NULL,0,'wei.ling@metrobank.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (838,'tom.dell@yahoo.com','Tom','Dell','2009-04-18 22:12:40','2009-03-26 08:10:59','1972-05-08 00:00:00',NULL,NULL,0,'tom.dell@tricapventures.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (922,'marksevard@yahoo.com','Mark','Sevard','2009-04-18 23:18:00','2009-03-27 11:40:56','1974-06-11 00:00:00',NULL,NULL,0,'mark.sevard@investco.com',NULL,NULL,'marks74',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1066,'amy.ng@gmail.com','Amy','Ng','2009-04-20 13:01:50','2009-03-27 12:03:21','1972-10-15 00:00:00',NULL,NULL,0,'amy@ng.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1117,'mary44@yahoo.com.com','Marybeth','Batchya','2009-04-18 22:26:05','2009-03-27 12:34:34','1968-05-08 00:00:00',NULL,NULL,0,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1189,'jrobininson@yahoo.com','Jeremy','Robinson','2009-04-16 23:37:32','2009-03-27 12:44:02','1974-12-06 00:00:00',NULL,NULL,0,'Jeremy.robinson@goldmansons.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1234,'hillary.taylor@metrobank.com','Hillary','Taylor','2009-04-18 22:45:19','2009-03-27 12:59:11','1979-11-01 00:00:00',NULL,NULL,0,'hillary.taylor@metrobank.com',NULL,NULL,'htaylor77','hb77',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1321,'jerry_wong@metrobank.com','Jerry','Wong','2009-04-20 06:19:25','2009-03-27 14:55:24','1973-02-15 00:00:00',NULL,NULL,0,'jerry_wong@metrobank.com','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1384,'krishna_guha@metrobank.com','Krishna','Guha','2009-04-18 23:03:37','2009-03-27 15:01:08','1965-09-08 00:00:00',NULL,NULL,0,'krishna_guha@metrobank.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (1486,'jenny.ang@lifetime.com','Jenny','Ang','2009-04-20 06:15:05','2009-03-27 15:09:58','1980-03-12 00:00:00',NULL,NULL,0,'jenny.ang@lifetime.com','jenny.ang@lifetime.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116565,'mary.bernard@yahoo.com','Mary','Bernard','2009-04-16 23:52:48','2009-03-31 09:46:22','1962-08-21 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116760,'marsha.guha@metrobank.com','Marsha','Guha','2009-04-16 14:41:07','2009-03-31 09:53:52','1963-06-15 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116820,'Marybeth.Smith@metrobank.com','Marybeth','Smith','2009-04-16 14:41:07','2009-03-31 10:09:05','1963-05-02 00:00:00',NULL,NULL,0,'marybeth.smith@metrobank.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116880,'dylan_tower@metrobank.com','Felicity','Towers','2009-04-20 13:27:01','2009-03-31 10:16:53','1982-12-26 00:00:00',NULL,NULL,0,'dylan_tower@metrobank.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116925,'anneyeo@gmail.com','Anne','Yeo','2009-04-17 00:00:55','2009-03-31 11:55:59','1985-10-20 00:00:00',NULL,NULL,0,'','anne.yeo@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (116982,'BarbaraJack@gmail.com','Barbara','Zajack','2009-04-20 13:34:23','2009-03-31 12:02:48','1958-04-23 00:00:00',NULL,NULL,0,'barbara@zojack.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (117015,'tina_shah@metrobank.com','Tina','Shah','2009-04-20 06:21:31','2009-03-31 12:09:41','1967-04-22 00:00:00',NULL,NULL,0,'tina_Shah@metrobank.com','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (117189,'aman@rocketmail.com','Aman','Mehta','2009-04-20 11:32:01','2009-03-31 12:54:08','1977-03-10 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (117240,'adesai@ITVendor.com','Ashish','Desai','2009-04-17 00:16:26','2009-03-31 13:01:54','1972-11-07 00:00:00',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;


--
-- Definition of table `person_certification`
--

DROP TABLE IF EXISTS `person_certification`;
CREATE TABLE `person_certification` (
  `PERSON_ID` int(10) unsigned NOT NULL,
  `TYPE` char(1) default NULL,
  `TITLE` varchar(80) default NULL,
  `DATE_CERTIFIED` datetime default NULL,
  `CERT_END_DATE` datetime default NULL,
  `CERT_ID` int(10) unsigned NOT NULL auto_increment,
  `ORDER_NO` smallint(5) unsigned default NULL,
  PRIMARY KEY  (`CERT_ID`),
  KEY `Index_2` (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `person_certification`
--

/*!40000 ALTER TABLE `person_certification` DISABLE KEYS */;
INSERT INTO `person_certification` (`PERSON_ID`,`TYPE`,`TITLE`,`DATE_CERTIFIED`,`CERT_END_DATE`,`CERT_ID`,`ORDER_NO`) VALUES 
 (1,'T','11111','2009-03-10 22:00:00','2009-03-01 22:00:00',325,0),
 (418,NULL,NULL,NULL,NULL,448,0),
 (595,'P','MST','2009-03-10 18:00:00',NULL,832,0),
 (116760,'P','Certified Benefits Specialist','2005-05-04 00:00:00',NULL,116814,0),
 (116820,'P','Novell Certified','1981-04-13 00:00:00',NULL,116874,0),
 (116880,'P','MCSC','2008-11-11 00:00:00',NULL,116903,0),
 (117015,'P','PMP','2009-01-05 00:00:00',NULL,117041,0),
 (117189,'I','Series 7','2006-07-10 00:00:00',NULL,117209,0),
 (117240,'P','Certified Microsoft Professional',NULL,NULL,117242,0),
 (838,'P','PMP','2006-12-11 00:00:00',NULL,123121,0),
 (1066,'P','Certified Business Analyst (CBA)','2005-07-06 00:00:00',NULL,125671,0);
/*!40000 ALTER TABLE `person_certification` ENABLE KEYS */;


--
-- Definition of table `person_education`
--

DROP TABLE IF EXISTS `person_education`;
CREATE TABLE `person_education` (
  `PERSON_ID` int(10) unsigned NOT NULL,
  `DEGREE_LEVEL` char(1) default NULL,
  `EDUCATIONAL_INSTITUTION` varchar(120) default NULL,
  `CONCENTRATION` varchar(45) default NULL,
  `SECOND_CONCENTRATION` varchar(45) default NULL,
  `THIRD_CONCENTRATION` varchar(45) default NULL,
  `GPA` smallint(5) unsigned default NULL,
  `EDUCATION_ID` int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (`EDUCATION_ID`),
  KEY `Index_1` USING BTREE (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `person_education`
--

/*!40000 ALTER TABLE `person_education` DISABLE KEYS */;
INSERT INTO `person_education` (`PERSON_ID`,`DEGREE_LEVEL`,`EDUCATIONAL_INSTITUTION`,`CONCENTRATION`,`SECOND_CONCENTRATION`,`THIRD_CONCENTRATION`,`GPA`,`EDUCATION_ID`) VALUES 
 (595,'B','Harvard University','Applied Mathematics','',NULL,99,835),
 (922,'B','NYU','Computer Science','',NULL,0,1006),
 (1066,'M','Stevens Institute of Technology','Project Management',NULL,NULL,0,1114),
 (1117,'B','Oxford University','MIS',NULL,NULL,0,1186),
 (1189,'H','Jackson High School','Business',NULL,NULL,0,1231),
 (1234,'B','NYU','Finance',NULL,NULL,0,1279),
 (1384,'M','Perth University','Computer Science',NULL,NULL,0,1452),
 (1486,'B','NYU','Business Management',NULL,NULL,0,1534),
 (116760,'M','Columbia University','Learning & Development',NULL,NULL,0,116817),
 (116820,'A','Hudson Community College','Management',NULL,NULL,0,116877),
 (116880,'B','NYIT','Computer Information System',NULL,NULL,0,116922),
 (116925,'B','Rutger\'s University','Finance',NULL,NULL,0,116951),
 (116982,'B','NJIT','CIS',NULL,NULL,0,116996),
 (117015,'M','Stevens Insitute of Technology','Information Systems','Project Management','Information Management',0,117042),
 (117015,'B','NYU','Finance',NULL,NULL,0,117090),
 (838,'B','Lehigh University','Finance',NULL,NULL,0,117186),
 (117189,'B','NYU','MIS',NULL,NULL,0,117210),
 (117240,'B','MS University','Computer Science',NULL,NULL,0,117243),
 (1321,'B','Rider Univerisity','Computer Science',NULL,NULL,0,122507),
 (1189,'M','College of London','Computer Design','Information Systems',NULL,0,123451),
 (1189,'B','Stanford University','Computer Science',NULL,NULL,0,125795);
/*!40000 ALTER TABLE `person_education` ENABLE KEYS */;


--
-- Definition of table `person_experience`
--

DROP TABLE IF EXISTS `person_experience`;
CREATE TABLE `person_experience` (
  `PERSON_ID` int(10) unsigned NOT NULL,
  `CAREER_LEVEL` char(1) default NULL,
  `COMPANY_NAME` varchar(60) default NULL,
  `COUNTRY` char(3) default NULL,
  `CITY` varchar(60) default NULL,
  `START_DATE` datetime default NULL,
  `END_DATE` datetime default NULL,
  `CURRENT_POSITION` varchar(1) default NULL,
  `MANAGER_NAME` varchar(40) default NULL,
  `MANAGED_PEOPLE_NO` smallint(5) unsigned zerofill default NULL,
  `CLASSES_TAKEN` varchar(255) default NULL,
  `COST_CENTER` varchar(255) default NULL,
  `EMPLOYEMENT_STATUS` char(1) default NULL,
  `AFFILIATION` varchar(45) default NULL,
  `EXP_ID` int(10) unsigned NOT NULL auto_increment,
  `COMPANY_ID` int(10) unsigned default NULL,
  `MANAGER_ID` varchar(45) default NULL,
  `DEPARTMENT` varchar(60) NOT NULL,
  PRIMARY KEY  (`EXP_ID`),
  KEY `Index_1` (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `person_experience`
--

/*!40000 ALTER TABLE `person_experience` DISABLE KEYS */;
INSERT INTO `person_experience` (`PERSON_ID`,`CAREER_LEVEL`,`COMPANY_NAME`,`COUNTRY`,`CITY`,`START_DATE`,`END_DATE`,`CURRENT_POSITION`,`MANAGER_NAME`,`MANAGED_PEOPLE_NO`,`CLASSES_TAKEN`,`COST_CENTER`,`EMPLOYEMENT_STATUS`,`AFFILIATION`,`EXP_ID`,`COMPANY_ID`,`MANAGER_ID`,`DEPARTMENT`) VALUES 
 (595,'A','Metrobank','US','New York','2009-01-01 00:00:00',NULL,'Y','Shawn Wong',00010,NULL,NULL,'F',NULL,631,12,NULL,'Corporate IT'),
 (595,'2','Company C','US','New York','2008-02-03 00:00:00','2008-12-01 00:00:00',NULL,'Dolores Woo',00000,NULL,NULL,'F',NULL,790,5,NULL,''),
 (0,NULL,'Metrobank',NULL,NULL,NULL,NULL,NULL,NULL,00000,NULL,NULL,NULL,NULL,845,12,NULL,''),
 (0,NULL,'Metrobank',NULL,NULL,NULL,NULL,NULL,NULL,00000,NULL,NULL,NULL,NULL,846,12,NULL,''),
 (595,'2','XYZ Company','US','New Jersey','2007-03-16 00:00:00','2008-01-16 00:00:00',NULL,'Jane Doe',00000,NULL,NULL,'F',NULL,847,6,NULL,'IT'),
 (595,'1','MetroBank','US','New York','2004-07-21 00:00:00','2007-02-14 00:00:00',NULL,'Alex Caper',00000,NULL,NULL,'F',NULL,886,7,NULL,'IT'),
 (922,'2','Baker\'s Bank','US','Jersey City','2008-03-03 00:00:00','2009-02-10 00:00:00',NULL,'Sue Higgines',00000,NULL,NULL,'C',NULL,923,8,NULL,'Equity Research'),
 (922,'A','InvestCo','US','New York','2009-02-16 00:00:00',NULL,'Y','Robert Bliss',00000,NULL,NULL,'V',NULL,925,12,NULL,'Treasury'),
 (1066,'A','Metrobank','US','New Jersey','2005-02-15 00:00:00','2009-01-05 00:00:00','N','Jane Doe',00005,NULL,NULL,'F',NULL,1090,12,NULL,'Research'),
 (1117,'A','Baker\'s Bank','GB','London','2005-10-05 00:00:00','2009-03-02 00:00:00','N','Kishna Guha',00000,NULL,NULL,'F',NULL,1144,12,NULL,'Equity Research IT'),
 (1189,'D','Goldman & Sons','GB','London','1979-09-03 00:00:00','2009-03-30 00:00:00','N','Jenny Ng',00075,NULL,NULL,'F',NULL,1210,12,NULL,'IT'),
 (1234,'A','Metrobank','US','New York','2006-04-21 00:00:00',NULL,'Y','Sonal Patel',00000,NULL,NULL,'F',NULL,1252,12,NULL,'Finance'),
 (1234,'2','US Trust Bank','US','New York','2004-06-14 00:00:00','2006-03-13 00:00:00',NULL,'Cindy Marks',00000,NULL,NULL,'F',NULL,1282,10,NULL,'IT'),
 (1321,'2','MetroBank','US','New York','2005-09-19 00:00:00',NULL,'Y','Krishna Guha',00000,NULL,NULL,'F',NULL,1342,12,NULL,'HR IT'),
 (1384,'D','Metrobank','GB','London','2008-06-23 00:00:00',NULL,'Y','Matt White',00003,NULL,NULL,'F',NULL,1385,12,NULL,'Equity IT'),
 (1384,'A','Baker\'s Bank','US','New Jersey ','2006-07-17 00:00:00','2008-06-19 00:00:00',NULL,'Alex Caper',00070,NULL,NULL,'F',NULL,1450,7,NULL,'Corporate Finance'),
 (1486,'1','Lifetime Banking','US','New York','2005-02-21 00:00:00',NULL,'Y','Daniel Markov',00000,NULL,NULL,'C',NULL,1501,12,NULL,'Sales & Trading'),
 (116565,'8','Goldman & Sons','US','New York','2007-03-16 00:00:00','2009-03-02 00:00:00','N','Sharon Rose',00020,NULL,NULL,'F',NULL,116566,12,NULL,'IBD'),
 (116760,'D','Metrobank','US','New Jersey','2007-01-16 00:00:00',NULL,'Y','Rhonda Hu',00020,NULL,NULL,'F',NULL,116781,12,NULL,'HR IT'),
 (116820,'A','Metrobank','US','Jersey City','2002-07-02 00:00:00',NULL,'Y','Barry Frank',00010,NULL,NULL,'F',NULL,116835,12,NULL,'IT'),
 (116880,'5','Metrobank','US','New York','1999-02-10 00:00:00',NULL,'Y','Shelly Guha',00002,NULL,NULL,'F',NULL,116901,12,NULL,'Corporate IT'),
 (116925,'2','Securities & Data Company','US','New York','1999-07-26 00:00:00','2009-03-02 00:00:00','Y','Terry Smith',00030,NULL,NULL,'F',NULL,116949,12,NULL,'Operations'),
 (116982,'A','Metrobank','US','New Jersey ','2002-06-26 00:00:00','2006-04-24 00:00:00','Y','Wei Ling',00004,NULL,NULL,'C',NULL,116994,12,NULL,'Pricing'),
 (117015,'2','Metrobank','US','New York','2009-02-09 00:00:00',NULL,'Y','Igor Reitman',00000,NULL,NULL,'C',NULL,117039,12,NULL,'Operations'),
 (117015,'2','Metrobank','US','Jersey City','2006-02-01 00:00:00','2008-09-09 00:00:00',NULL,'John Ricci',00002,NULL,NULL,'F',NULL,117093,9,NULL,'Operations Technology'),
 (838,'2','TriCap Ventures','US','New York','2008-09-01 00:00:00',NULL,'Y','Phil Biggle',00000,NULL,NULL,'C',NULL,117153,12,NULL,'IT'),
 (117189,'2','ABC Company','US','New York','2008-12-04 00:00:00','2009-03-30 00:00:00','N','Joe Maggio',00005,NULL,NULL,'C',NULL,117207,9,NULL,'Treasury'),
 (117240,'2','ABC Company','IN','Mumbai','2007-11-01 00:00:00',NULL,'Y','Reynold Parson',00000,NULL,NULL,'V',NULL,117241,12,NULL,'Fixed Income'),
 (117189,'2','Metrobank','US','New York','2006-01-02 00:00:00','2008-08-04 00:00:00','N','Marsha Guha',00000,NULL,NULL,'F',NULL,122525,12,NULL,'IT'),
 (116982,'9','Zojack Consulting','US','New York','2006-06-12 00:00:00',NULL,'Y','Tanya Blue',00000,NULL,NULL,'V',NULL,122772,0,NULL,'Operations IT'),
 (1321,'2','Bank of Manhattan','US','New York','2003-06-02 00:00:00','2005-01-24 00:00:00',NULL,'Cody Johnson',00000,NULL,NULL,'C',NULL,124037,0,NULL,'HR'),
 (116820,'A','Baker\'s Bank','US','Jersey City','1998-02-05 00:00:00','2002-05-27 00:00:00',NULL,'John Smith',00000,NULL,NULL,'F',NULL,124475,0,NULL,'IT'),
 (1066,'A',NULL,'US','New York','2009-01-06 00:00:00',NULL,'Y',NULL,00000,NULL,NULL,'C',NULL,126863,0,NULL,'Finance'),
 (1117,NULL,'','GB','London','2009-03-03 00:00:00',NULL,'Y',NULL,00000,NULL,NULL,'C',NULL,127082,0,NULL,'Corporate'),
 (1189,NULL,'JR Consulting','GB','London','2009-04-01 00:00:00',NULL,'Y',NULL,00000,NULL,NULL,'V',NULL,127206,0,NULL,'Finance'),
 (116565,NULL,'Helpdesk Associates','US','New York','2009-04-01 00:00:00',NULL,'Y',NULL,00000,NULL,NULL,'V',NULL,127290,0,NULL,'IT'),
 (116925,NULL,NULL,'US','New York',NULL,'2009-03-23 00:00:00','Y',NULL,00000,NULL,NULL,'C',NULL,127372,0,NULL,'Operations');
/*!40000 ALTER TABLE `person_experience` ENABLE KEYS */;


--
-- Definition of table `person_language`
--

DROP TABLE IF EXISTS `person_language`;
CREATE TABLE `person_language` (
  `TAXONOMY_ID` int(10) unsigned NOT NULL,
  `PERSON_ID` int(10) unsigned NOT NULL,
  `ORDER_NO` tinyint(3) unsigned default NULL,
  `LEVEL` char(1) default NULL,
  PRIMARY KEY  (`TAXONOMY_ID`,`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person_language`
--

/*!40000 ALTER TABLE `person_language` DISABLE KEYS */;
/*!40000 ALTER TABLE `person_language` ENABLE KEYS */;


--
-- Definition of table `person_profile`
--

DROP TABLE IF EXISTS `person_profile`;
CREATE TABLE `person_profile` (
  `PERSON_ID` int(10) unsigned NOT NULL,
  `WILLING_RELOCATE` char(1) default NULL,
  `WILLING_TRAVEL` tinyint(3) unsigned default NULL,
  `AUTHORIZED_WORKER_COUNTRIES` varchar(255) default NULL,
  `SPONSORSHIP_REQUIRED` char(1) default NULL,
  `ETHNICITY` varchar(1) default NULL,
  `GENDER` char(1) default NULL,
  `HOBBIES` varchar(255) default NULL,
  `LANG_SPEAK` varchar(127) default NULL,
  `LANG_READ` varchar(127) default NULL,
  `MUSIC_TYPE` varchar(127) default NULL,
  `FAVORITE_MUSICIANS` varchar(255) default NULL,
  `FAVORITE_AUTHOR` varchar(255) default NULL,
  `RECOMMENDED_BOOKS` varchar(255) default NULL,
  `READ_BOOKS` varchar(511) default NULL,
  `TRAVELED_LOCATIONS` varchar(255) default NULL,
  `PERSONAL_WEBSITE` varchar(80) default NULL,
  `FAVORITE_WEBSITE` varchar(80) default NULL,
  `FOLLOWING_WEBSITES` varchar(255) default NULL,
  `FOLLOWING_BLOGS` varchar(511) default NULL,
  `FAVORITE_NEWS_CHANNEL` varchar(45) default NULL,
  `READ_NEWSPAPERS` varchar(255) default NULL,
  `MAGAZINES_SUBSCRIPTIONS` varchar(255) default NULL,
  `FAVORITE_TV_SHOW` varchar(60) default NULL,
  `FAVORITE_QUOTATIONS` varchar(45) default NULL,
  `POLITICAL_VIEW` varchar(127) default NULL,
  `RELIGIOUS_VIEW` varchar(127) default NULL,
  `PHILANTHROPY` varchar(127) default NULL,
  `AWARDS` varchar(255) default NULL,
  `AFFILIATION` varchar(255) default NULL,
  `DO_MENTOR` char(1) default NULL,
  `FAVORITE_PROFS_ACCOMP` varchar(127) default NULL,
  `CAREER_STATUS` char(1) default NULL,
  `EXPERIENCE_YEARS` tinyint(3) unsigned default NULL,
  `CAREER_MUST_HAVES` varchar(255) default NULL,
  `PREFERRED_SALARY_RANGE` smallint(5) unsigned default NULL,
  `PREFERRED_COMPANY_SIZE` tinyint(3) unsigned default NULL,
  `PREFERRED_COMPANY_TYPE` tinyint(3) unsigned default NULL,
  `PREFERRED_CAREER_LEVEL` char(1) default NULL,
  `MILITARY_SERVED` char(1) default NULL,
  `SECURITY_CLEARANCE` char(1) default NULL,
  `STAFF_MANAGED` char(1) default NULL,
  `GLOBAL_APPLICATIONS` char(1) default NULL,
  `TRADING_APPLICATIONS` char(1) default NULL,
  `DEPARTMENT` varchar(45) NOT NULL,
  `SUPERVISER` varchar(45) NOT NULL,
  `AVAILABILITY_FLAG` char(1) default NULL,
  `AVAILABILITY_FROM` datetime default NULL,
  `AVAILABILITY_TILL` datetime default NULL,
  `COST_CATEGORY` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `person_profile`
--

/*!40000 ALTER TABLE `person_profile` DISABLE KEYS */;
INSERT INTO `person_profile` (`PERSON_ID`,`WILLING_RELOCATE`,`WILLING_TRAVEL`,`AUTHORIZED_WORKER_COUNTRIES`,`SPONSORSHIP_REQUIRED`,`ETHNICITY`,`GENDER`,`HOBBIES`,`LANG_SPEAK`,`LANG_READ`,`MUSIC_TYPE`,`FAVORITE_MUSICIANS`,`FAVORITE_AUTHOR`,`RECOMMENDED_BOOKS`,`READ_BOOKS`,`TRAVELED_LOCATIONS`,`PERSONAL_WEBSITE`,`FAVORITE_WEBSITE`,`FOLLOWING_WEBSITES`,`FOLLOWING_BLOGS`,`FAVORITE_NEWS_CHANNEL`,`READ_NEWSPAPERS`,`MAGAZINES_SUBSCRIPTIONS`,`FAVORITE_TV_SHOW`,`FAVORITE_QUOTATIONS`,`POLITICAL_VIEW`,`RELIGIOUS_VIEW`,`PHILANTHROPY`,`AWARDS`,`AFFILIATION`,`DO_MENTOR`,`FAVORITE_PROFS_ACCOMP`,`CAREER_STATUS`,`EXPERIENCE_YEARS`,`CAREER_MUST_HAVES`,`PREFERRED_SALARY_RANGE`,`PREFERRED_COMPANY_SIZE`,`PREFERRED_COMPANY_TYPE`,`PREFERRED_CAREER_LEVEL`,`MILITARY_SERVED`,`SECURITY_CLEARANCE`,`STAFF_MANAGED`,`GLOBAL_APPLICATIONS`,`TRADING_APPLICATIONS`,`DEPARTMENT`,`SUPERVISER`,`AVAILABILITY_FLAG`,`AVAILABILITY_FROM`,`AVAILABILITY_TILL`,`COST_CATEGORY`) VALUES 
 (0,'',0,NULL,NULL,NULL,'M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0,0,0,'0',NULL,NULL,NULL,NULL,NULL,'IT','Daniel Markov',NULL,NULL,NULL,NULL),
 (595,'Y',2,'US',NULL,'A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',4,NULL,125,7,1,'D',NULL,NULL,'Y',NULL,NULL,'Sales & Trading','Sharon Rose','N','2009-08-03 00:00:00','2009-12-31 00:00:00',4),
 (838,'',2,NULL,'Y','W','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',3,NULL,80,4,4,'2',NULL,NULL,NULL,NULL,NULL,'IB','Rhonda Hu','L','2009-04-27 00:00:00','2009-12-31 00:00:00',2),
 (922,'N',1,NULL,NULL,'H','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',3,NULL,90,8,1,'3',NULL,NULL,'Y',NULL,NULL,'HR','Barry Frank','L','2009-07-01 00:00:00',NULL,3),
 (1066,'Y',2,NULL,NULL,'A','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',3,NULL,85,5,2,'3',NULL,NULL,'Y',NULL,NULL,'Legal','Rhonda Hu','A','2010-01-04 00:00:00',NULL,2),
 (1117,'Y',2,NULL,'Y','H','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'A',3,NULL,110,3,1,'D',NULL,NULL,NULL,NULL,NULL,'Operations','Shelly Guha','A',NULL,NULL,3),
 (1189,'Y',2,NULL,NULL,'W','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',6,NULL,150,8,1,'5',NULL,NULL,'Y',NULL,NULL,'IT','Rhonda Hu','A',NULL,NULL,4),
 (1234,'N',2,NULL,NULL,'B','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',3,NULL,95,5,2,'8',NULL,NULL,NULL,NULL,NULL,'IT','Barry Frank','N',NULL,NULL,3),
 (1321,NULL,1,NULL,'Y','A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'U',3,NULL,110,5,0,'4',NULL,NULL,NULL,NULL,NULL,'IT','Terry Smith','N','2010-01-18 00:00:00',NULL,3),
 (1384,'Y',1,NULL,NULL,'A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',4,NULL,120,5,1,'3',NULL,NULL,'Y',NULL,NULL,'Sales & Trading','Rhonda Hu','N','2009-10-05 00:00:00','2010-03-31 00:00:00',3),
 (1486,NULL,1,NULL,NULL,'A','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',4,NULL,55,5,4,'2',NULL,NULL,'Y',NULL,NULL,'Sales & Trading','Shelly Guha','L','2009-06-01 00:00:00',NULL,1),
 (116565,NULL,2,NULL,NULL,'B','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'A',4,NULL,150,5,1,'2',NULL,NULL,'Y',NULL,NULL,'Sales & Trading','Kelly Blue','A',NULL,NULL,4),
 (116760,NULL,2,NULL,NULL,'I','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',4,NULL,140,3,1,'3',NULL,NULL,NULL,NULL,NULL,'Legal','Barry Frank','N','2009-05-04 00:00:00',NULL,4),
 (116820,NULL,1,NULL,NULL,'B','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'U',4,NULL,110,5,1,'5',NULL,NULL,'Y',NULL,NULL,'Legal','Rhonda Hu','N','2009-06-01 00:00:00',NULL,3),
 (116880,NULL,1,NULL,NULL,'W','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'E',5,NULL,250,5,2,'4',NULL,NULL,NULL,NULL,NULL,'IB','Terry Smith','N',NULL,NULL,4),
 (116925,NULL,1,NULL,NULL,'A','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',4,NULL,45,8,1,'2',NULL,NULL,NULL,NULL,NULL,'Operations','Rhonda Hu','A','2009-11-09 00:00:00',NULL,1),
 (116982,NULL,3,NULL,NULL,'W','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',5,NULL,100,5,3,'3',NULL,NULL,'Y',NULL,NULL,'Legal','Shelly Guha','L','2009-07-20 00:00:00',NULL,3),
 (117015,NULL,2,NULL,NULL,'A','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',4,NULL,55,7,1,'9',NULL,NULL,'Y',NULL,NULL,'HR','Terry Smith','A',NULL,NULL,1),
 (117189,NULL,3,'US',NULL,'A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Y',NULL,'A',4,NULL,75,5,3,'2',NULL,NULL,NULL,NULL,NULL,'IB','Barry Frank','A',NULL,NULL,2),
 (117240,'Y',3,'India',NULL,'A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'U',4,NULL,30,3,1,'2',NULL,NULL,NULL,NULL,NULL,'IB','Rhonda Hu','L','2009-05-18 00:00:00',NULL,1);
/*!40000 ALTER TABLE `person_profile` ENABLE KEYS */;


--
-- Definition of table `person_skill`
--

DROP TABLE IF EXISTS `person_skill`;
CREATE TABLE `person_skill` (
  `PERSON_ID` int(10) unsigned NOT NULL auto_increment,
  `TAXONOMY_ID` int(10) unsigned NOT NULL,
  `LEVEL` tinyint(3) unsigned NOT NULL,
  `ORDER_NO` smallint(5) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (`PERSON_ID`,`TAXONOMY_ID`),
  KEY `Index_2` (`TAXONOMY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person_skill`
--

/*!40000 ALTER TABLE `person_skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `person_skill` ENABLE KEYS */;


--
-- Definition of table `project_profile`
--

DROP TABLE IF EXISTS `project_profile`;
CREATE TABLE `project_profile` (
  `PROJECT_CODE` varchar(30) default NULL,
  `TITLE` varchar(120) default NULL,
  `DESCRIPTION` varchar(2045) default NULL,
  `STATUS` char(1) default NULL,
  `START_DATE` datetime default NULL,
  `END_DATE` datetime default NULL,
  `OWNER` int(10) unsigned default NULL,
  `PROJECT_ID` int(10) unsigned NOT NULL auto_increment,
  `EXP_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`PROJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `project_profile`
--

/*!40000 ALTER TABLE `project_profile` DISABLE KEYS */;
INSERT INTO `project_profile` (`PROJECT_CODE`,`TITLE`,`DESCRIPTION`,`STATUS`,`START_DATE`,`END_DATE`,`OWNER`,`PROJECT_ID`,`EXP_ID`) VALUES 
 (NULL,NULL,NULL,NULL,NULL,NULL,0,436,427),
 ('111111','111111','1111111','D','2009-03-10 22:00:00',NULL,0,445,442),
 ('Derivative Swap','Derivative Project','','C',NULL,NULL,0,791,790),
 ('XYZ-001','Test Pilot Project',NULL,'C','2007-04-01 00:00:00','2007-12-28 00:00:00',0,848,847),
 ('987','Pinksheets',NULL,'C','2006-03-01 00:00:00','2007-02-22 00:00:00',0,887,886),
 ('PALS123','Equity PALS Project',NULL,'D','2008-03-10 00:00:00','2008-06-30 00:00:00',0,924,923),
 ('EM987','Entity Master',NULL,'D','2009-02-02 00:00:00','2009-06-30 00:00:00',0,926,925),
 ('876','Audit Compliance ',NULL,'C','2005-03-01 00:00:00','2008-12-29 00:00:00',0,1091,1090),
 ('011','Research for Today',NULL,'C','2006-03-06 00:00:00','2009-02-02 00:00:00',0,1145,1144),
 ('C3098','Technology in Banks',NULL,'C','2001-03-01 00:00:00','2009-03-27 00:00:00',0,1211,1210),
 ('FAS0911','FAS911 Upgrade',NULL,'C','2006-05-01 00:00:00','2009-04-30 00:00:00',0,1258,1252),
 ('764','Corporate Conversion',NULL,'C','2006-03-20 00:00:00','2009-03-23 00:00:00',0,1283,1282),
 ('VC Conversion','Vendor Conversion',NULL,'D','2008-03-03 00:00:00','2009-12-24 00:00:00',0,1343,1342),
 ('CC123','Equity Conversion',NULL,'D','2008-07-01 00:00:00','2009-09-28 00:00:00',0,1411,1385),
 ('125','Pinksheets',NULL,'C',NULL,NULL,0,1451,1450),
 ('CMC2008','Captial Market Credit',NULL,'I','2009-03-03 00:00:00','2009-05-25 00:00:00',0,1502,1501),
 ('IB007','IB Today',NULL,'D','2007-04-02 00:00:00','2009-03-02 00:00:00',0,116567,116566),
 ('HR101','HR Vendor Conversion',NULL,'R','2007-01-16 00:00:00','2009-04-30 00:00:00',0,116782,116781),
 ('CC-456','Corporate Coversion',NULL,'I','2008-04-01 00:00:00','2009-02-27 00:00:00',0,116836,116835),
 ('Equity Compliance','Equity Compliance',NULL,'C','2001-04-04 00:00:00','2005-03-29 00:00:00',0,116902,116901),
 ('411','Options Pricing',NULL,'R','2008-12-29 00:00:00','2009-05-26 00:00:00',0,116950,116949),
 ('Bloomberg2005','Bloomberg Rec',NULL,'C','2005-03-01 00:00:00','2008-12-08 00:00:00',0,116995,116994),
 ('CRM101','CRM',NULL,'D','2009-03-02 00:00:00','2009-06-30 00:00:00',0,117040,117039),
 ('Blue100','Bluesheets',NULL,'C','2007-01-01 00:00:00','2008-01-07 00:00:00',0,117094,117093),
 ('SWAPs','Swap Conversion',NULL,'S','2009-01-06 00:00:00',NULL,0,117208,117207),
 ('DC2009','DirectConnect',NULL,'D','2009-01-01 00:00:00','2009-07-31 00:00:00',0,122492,631),
 ('CC984','Collateral Collect',NULL,'R','2009-01-01 00:00:00','2009-07-31 00:00:00',0,122495,117153),
 ('456','Test Drug IQ',NULL,'I','2008-01-01 00:00:00','2008-04-30 00:00:00',0,122526,122525),
 ('CC123','Credit Card Services',NULL,'D','2008-04-30 00:00:00','2009-04-23 00:00:00',0,122561,117241),
 ('345','The Big Conversion',NULL,'D','2007-04-16 00:00:00','2009-06-29 00:00:00',0,122773,122772),
 ('PALS123','Equity Pals Project',NULL,'C',NULL,NULL,0,124476,124475);
/*!40000 ALTER TABLE `project_profile` ENABLE KEYS */;


--
-- Definition of table `project_relation`
--

DROP TABLE IF EXISTS `project_relation`;
CREATE TABLE `project_relation` (
  `RELATION_ID` int(10) unsigned NOT NULL auto_increment,
  `PROJECT_ID` int(10) unsigned NOT NULL,
  `OBJECT_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`RELATION_ID`),
  KEY `Index_2` (`PROJECT_ID`,`OBJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_relation`
--

/*!40000 ALTER TABLE `project_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_relation` ENABLE KEYS */;


--
-- Definition of table `role_profile`
--

DROP TABLE IF EXISTS `role_profile`;
CREATE TABLE `role_profile` (
  `ROLE_ID` int(10) unsigned NOT NULL auto_increment,
  `NAME` varchar(45) default NULL,
  `PROJECT` varchar(30) default NULL,
  `START_DATE` datetime default NULL,
  `END_DATE` datetime default NULL,
  `LOCATION` varchar(45) default NULL,
  `HIRING_MANAGER` varchar(60) default NULL,
  `POSITION_TYPE` char(1) default NULL,
  `DESCRIPTION` varchar(511) default NULL,
  `PUBLIC` char(1) default NULL,
  PRIMARY KEY  (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_profile`
--

/*!40000 ALTER TABLE `role_profile` DISABLE KEYS */;
INSERT INTO `role_profile` (`ROLE_ID`,`NAME`,`PROJECT`,`START_DATE`,`END_DATE`,`LOCATION`,`HIRING_MANAGER`,`POSITION_TYPE`,`DESCRIPTION`,`PUBLIC`) VALUES 
 (823,'Senior Developer','dossier','2009-04-09 21:00:00','2009-06-11 21:00:00',NULL,'Alex','F','','Y'),
 (826,'DBA','dossier','2009-05-07 21:00:00','2009-10-15 21:00:00',NULL,'Alex',NULL,NULL,NULL),
 (2835,'QA Manager','dossier','2009-07-15 21:00:00','2010-02-19 22:00:00',NULL,'my test manager',NULL,NULL,NULL),
 (39018,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (50522,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (81523,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (83026,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (83533,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (104035,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (104537,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (105039,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (105543,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (106044,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (106545,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (107559,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (111560,NULL,NULL,NULL,NULL,NULL,'','F',NULL,'Y'),
 (116064,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (118411,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (118912,'Developer','Dossier','2009-04-03 00:00:00','2009-04-16 00:00:00',NULL,'Alex Manager','F','Test role',NULL),
 (119917,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `role_profile` ENABLE KEYS */;


--
-- Definition of table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `SEQ` int(10) unsigned NOT NULL,
  `SEQ_TYPE` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sequence`
--

/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
INSERT INTO `sequence` (`SEQ`,`SEQ_TYPE`) VALUES 
 (132850,'A'),
 (132850,'A');
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;


--
-- Definition of table `taxonomy`
--

DROP TABLE IF EXISTS `taxonomy`;
CREATE TABLE `taxonomy` (
  `TAXONOMY_ID` int(10) unsigned NOT NULL auto_increment,
  `TAXONOMY_TYPE` char(30) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  `PARENT` int(10) unsigned default NULL,
  `AUTOCOMPLETE_RATING` int(10) unsigned default NULL,
  `REVIEWED` char(1) default NULL,
  `DATE_CREATED` datetime NOT NULL,
  PRIMARY KEY  (`TAXONOMY_ID`),
  UNIQUE KEY `Index_2` USING BTREE (`NAME`,`TAXONOMY_TYPE`),
  FULLTEXT KEY `Index_3` (`NAME`)
) ENGINE=MyISAM AUTO_INCREMENT=132279 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `taxonomy`
--

/*!40000 ALTER TABLE `taxonomy` DISABLE KEYS */;
INSERT INTO `taxonomy` (`TAXONOMY_ID`,`TAXONOMY_TYPE`,`NAME`,`PARENT`,`AUTOCOMPLETE_RATING`,`REVIEWED`,`DATE_CREATED`) VALUES 
 (617,'j','Developer',0,0,NULL,'2009-03-26 07:29:17'),
 (618,'j','Project Manager',0,0,NULL,'2009-03-26 07:29:22'),
 (613,'w','Shanghai',0,0,NULL,'2009-03-26 07:29:47'),
 (612,'w','Beijing',0,0,NULL,'2009-03-26 07:29:42'),
 (611,'w','London',0,0,NULL,'2009-03-26 07:29:35'),
 (601,'e','Swaps',0,0,NULL,'2009-03-26 07:33:16'),
 (603,'s','J2EE',0,0,NULL,'2009-03-26 07:33:21'),
 (604,'s','.Net',0,0,NULL,'2009-03-26 07:33:28'),
 (607,'l','English',0,0,NULL,'2009-03-26 07:33:43'),
 (608,'l','Cantonese',0,0,NULL,'2009-03-26 07:33:54'),
 (642,'x','Consultant',0,0,NULL,'2009-03-26 07:55:11'),
 (634,'z','Warrants',0,0,NULL,'2009-03-26 07:57:30'),
 (635,'z','Options',0,0,NULL,'2009-03-26 07:57:28'),
 (636,'z','Derivative',0,0,NULL,'2009-03-26 07:57:25'),
 (632,'z','Swaps',0,0,NULL,'2009-03-26 07:57:03'),
 (633,'z','Convertables',0,0,NULL,'2009-03-26 07:57:09'),
 (645,'t','.Net',0,0,NULL,'2009-03-26 07:58:18'),
 (644,'t','Java',0,0,NULL,'2009-03-26 07:58:11'),
 (647,'t','Java Test',0,0,NULL,'2009-03-26 07:58:37'),
 (648,'t','Java Beans',0,0,NULL,'2009-03-26 07:58:42'),
 (646,'t','Javascript',0,0,NULL,'2009-03-26 07:58:25'),
 (657,'p','Bill Nylander',0,0,NULL,'2009-03-26 07:58:03'),
 (654,'p','Cindy Marks',0,0,NULL,'2009-03-26 07:57:44'),
 (655,'p','Jenny Ng',0,0,NULL,'2009-03-26 07:57:50'),
 (656,'p','Mary Huang',0,0,NULL,'2009-03-26 07:57:56'),
 (664,'i','Financial Services',0,0,NULL,'2009-03-26 07:55:28'),
 (662,'a','Technology',0,0,NULL,'2009-03-26 07:55:37'),
 (797,'x','Developer',0,0,NULL,'2009-03-26 08:01:03'),
 (793,'z','Bonds',0,0,NULL,'2009-03-26 08:03:01'),
 (792,'z','Derivatives',0,0,NULL,'2009-03-26 08:02:50'),
 (799,'a','Investment Banking',0,0,NULL,'2009-03-26 08:02:29'),
 (814,'p','Rae Ling',0,0,NULL,'2009-03-26 08:04:11'),
 (815,'p','Tess Chung',0,0,NULL,'2009-03-26 08:04:19'),
 (816,'p','Ashish Patel',0,0,NULL,'2009-03-26 08:04:26'),
 (824,'t','SQL',0,0,NULL,'2009-03-26 08:04:47'),
 (823,'t','Perl',0,0,NULL,'2009-03-26 08:04:36'),
 (850,'z','Stocks',0,0,NULL,'2009-03-27 11:21:16'),
 (849,'z','Bond',0,0,NULL,'2009-03-27 11:21:13'),
 (853,'t','J2EE',0,0,NULL,'2009-03-27 11:21:29'),
 (858,'a','Information Technology',0,0,NULL,'2009-03-27 11:22:05'),
 (861,'x','Full Time',0,0,NULL,'2009-03-27 11:23:39'),
 (890,'x','Technical Analyst',0,0,NULL,'2009-03-27 11:26:08'),
 (893,'p','Jane Doe',0,0,NULL,'2009-03-27 11:28:15'),
 (894,'p','John Ripley',0,0,NULL,'2009-03-27 11:28:27'),
 (898,'a','IT',0,0,NULL,'2009-03-27 11:27:49'),
 (1008,'p','John James',0,0,NULL,'2009-03-27 11:38:55'),
 (1007,'p','Wei Ling',0,0,NULL,'2009-03-27 11:38:49'),
 (1060,'l','Spanish',0,0,NULL,'2009-03-27 11:41:11'),
 (1073,'w','New York',0,0,NULL,'2009-03-27 12:03:39'),
 (1084,'l','Japanese',0,0,NULL,'2009-03-27 12:05:57'),
 (1126,'j','Technical Analyst',0,0,NULL,'2009-03-27 12:32:51'),
 (1122,'w','Mumbai',0,0,NULL,'2009-03-27 12:33:07'),
 (1118,'l','Hindi',0,0,NULL,'2009-03-27 12:33:53'),
 (1200,'j','Programmer',0,0,NULL,'2009-03-27 12:43:56'),
 (1201,'j','Data Analyst',0,0,NULL,'2009-03-27 12:44:06'),
 (1198,'w','Tokyo',0,0,NULL,'2009-03-27 12:44:12'),
 (1212,'x','Data Analyst',0,0,NULL,'2009-03-27 12:44:39'),
 (1216,'a','Research & Development',0,0,NULL,'2009-03-27 12:53:36'),
 (1239,'j','Business Analyst',0,0,NULL,'2009-03-27 12:57:54'),
 (1235,'l','French',0,0,NULL,'2009-03-27 12:58:50'),
 (1253,'x','Fashion Consultant',0,0,NULL,'2009-03-27 12:59:50'),
 (1259,'z','Word',0,0,NULL,'2009-03-27 13:47:37'),
 (1261,'z','Project',0,0,NULL,'2009-03-27 13:47:51'),
 (1260,'z','Excel',0,0,NULL,'2009-03-27 13:47:44'),
 (1265,'t','C++',0,0,NULL,'2009-03-27 13:48:02'),
 (1268,'p','Nina Bass',0,0,NULL,'2009-03-27 13:48:11'),
 (1288,'x','Business Analyst',0,0,NULL,'2009-03-27 13:51:01'),
 (1292,'p','Cindy Chatterton',0,0,NULL,'2009-03-27 13:52:34'),
 (1293,'p','Lucy Lee',0,0,NULL,'2009-03-27 13:52:39'),
 (1296,'a','Corporate Security',0,0,NULL,'2009-03-27 13:52:58'),
 (1324,'w','France',0,0,NULL,'2009-03-27 14:54:03'),
 (1350,'x','Vendor',0,0,NULL,'2009-03-27 14:55:21'),
 (1344,'z','Convertibles',0,0,NULL,'2009-03-27 14:57:22'),
 (1354,'p','Marybeth Batchya',0,0,NULL,'2009-03-27 14:57:45'),
 (1355,'p','Krishna Guha',0,0,NULL,'2009-03-27 14:57:51'),
 (1360,'i','Bank',0,0,NULL,'2009-03-27 14:57:55'),
 (1358,'a','Credit Card Services',0,0,NULL,'2009-03-27 14:58:04'),
 (1399,'j','Manager',0,0,NULL,'2009-03-27 15:01:02'),
 (1393,'l','Tamil',0,0,NULL,'2009-03-27 15:02:05'),
 (1414,'z','Convertible',0,0,NULL,'2009-03-27 15:03:56'),
 (1412,'z','Stock',0,0,NULL,'2009-03-27 15:03:39'),
 (1413,'z','Option',0,0,NULL,'2009-03-27 15:03:42'),
 (1423,'p','Jerry Wong',0,0,NULL,'2009-03-27 15:04:15'),
 (1426,'a','Credit Card Authorization',0,0,NULL,'2009-03-27 15:04:55'),
 (1455,'x','Technical Manager',0,0,NULL,'2009-03-27 15:06:16'),
 (1494,'j','Sales & Trading',0,0,NULL,'2009-03-27 15:09:53'),
 (1509,'x','Trader',0,0,NULL,'2009-03-27 15:10:57'),
 (1503,'z','CDO\'s',0,0,NULL,'2009-03-27 15:12:00'),
 (1504,'z','CDS',0,0,NULL,'2009-03-27 15:12:12'),
 (1511,'t','Excel',0,0,NULL,'2009-03-27 15:12:24'),
 (1512,'t','Word',0,0,NULL,'2009-03-27 15:12:28'),
 (1515,'a','Sale & Trading',0,0,NULL,'2009-03-27 15:12:42'),
 (105544,'s','java',0,0,NULL,'2009-03-30 11:56:40'),
 (106045,'j','Senior Developer',0,0,NULL,'2009-03-30 11:59:36'),
 (106547,'s','SAP EP',0,0,NULL,'2009-03-30 12:00:31'),
 (107046,'s','SAP Web Application Server',0,0,NULL,'2009-03-30 12:01:11'),
 (116067,'s','C#',0,0,NULL,'2009-03-31 06:39:03'),
 (116572,'j','Banker',0,0,NULL,'2009-03-31 09:37:31'),
 (116568,'l','Italian',0,0,NULL,'2009-03-31 09:39:05'),
 (116576,'x','M&A Banker',0,0,NULL,'2009-03-31 09:39:32'),
 (116574,'z','M&A',0,0,NULL,'2009-03-31 09:40:40'),
 (116578,'t','Powerpoint',0,0,NULL,'2009-03-31 09:40:57'),
 (116582,'p','Jenny Ang',0,0,NULL,'2009-03-31 09:44:40'),
 (116766,'j','HR Manager',0,0,NULL,'2009-03-31 09:53:02'),
 (116761,'l','Gujarati',0,0,NULL,'2009-03-31 09:53:46'),
 (116789,'x','HR Manager',0,0,NULL,'2009-03-31 09:54:21'),
 (116783,'z','Benefit',0,0,NULL,'2009-03-31 09:55:31'),
 (116785,'z','Recruiting',0,0,NULL,'2009-03-31 09:55:48'),
 (116784,'z','Compensation',0,0,NULL,'2009-03-31 09:55:36'),
 (116792,'t','Peoplesoft',0,0,NULL,'2009-03-31 09:55:56'),
 (116791,'t','HRIS',0,0,NULL,'2009-03-31 09:55:51'),
 (116823,'j','IT Manager',0,0,NULL,'2009-03-31 10:08:38'),
 (116845,'x','IT Manager',0,0,NULL,'2009-03-31 10:09:39'),
 (116840,'z','HR',0,0,NULL,'2009-03-31 10:11:54'),
 (116837,'z','MTS',0,0,NULL,'2009-03-31 10:11:45'),
 (116839,'z','ITS',0,0,NULL,'2009-03-31 10:11:50'),
 (116838,'z','CRM',0,0,NULL,'2009-03-31 10:11:48'),
 (116849,'p','Marsha Guha',0,0,NULL,'2009-03-31 10:11:04'),
 (116850,'p','Mary Barnard',0,0,NULL,'2009-03-31 10:11:14'),
 (116885,'j','General Counsel',0,0,NULL,'2009-03-31 10:16:07'),
 (116886,'j','Lawyer',0,0,NULL,'2009-03-31 10:16:12'),
 (116881,'l','German',0,0,NULL,'2009-03-31 10:16:50'),
 (116904,'x','General Counsel',0,0,NULL,'2009-03-31 10:17:26'),
 (116906,'p','Shelly Guha',0,0,NULL,'2009-03-31 10:18:25'),
 (116908,'a','Legal & Compliance',0,0,NULL,'2009-03-31 10:18:40'),
 (116933,'j','Operations Manager',0,0,NULL,'2009-03-31 11:37:07'),
 (116932,'j','Pricing Manager',0,0,NULL,'2009-03-31 11:31:24'),
 (116929,'w','New Jersey',0,0,NULL,'2009-03-31 11:38:02'),
 (116926,'l','Chinese',0,0,NULL,'2009-03-31 11:55:57'),
 (116957,'x','Pricing Manager',0,0,NULL,'2009-03-31 11:56:37'),
 (116952,'z','Equity',0,0,NULL,'2009-03-31 11:58:50'),
 (116953,'z','Fixed Income',0,0,NULL,'2009-03-31 11:58:54'),
 (116961,'a','Operations',0,0,NULL,'2009-03-31 11:59:38'),
 (116984,'j','QA Manager',0,0,NULL,'2009-03-31 12:01:45'),
 (116997,'x','QA Manager',0,0,NULL,'2009-03-31 12:03:24'),
 (116999,'p','Marybeth Smith',0,0,NULL,'2009-03-31 12:04:38'),
 (117044,'z','Peoplesoft',0,0,NULL,'2009-03-31 12:11:24'),
 (117045,'z','HRIS',0,0,NULL,'2009-03-31 12:11:36'),
 (117046,'z','Visio',0,0,NULL,'2009-03-31 12:37:15'),
 (117051,'t','Adobe',0,0,NULL,'2009-03-31 12:37:20'),
 (117056,'p','Tasha Johnson',0,0,NULL,'2009-03-31 12:38:02'),
 (117055,'p','Jana Taylor',0,0,NULL,'2009-03-31 12:37:45'),
 (117057,'p','Rebecca Firman',0,0,NULL,'2009-03-31 12:38:16'),
 (117101,'x','Project Manager',0,0,NULL,'2009-03-31 12:40:31'),
 (117095,'z','Bluesheets',0,0,NULL,'2009-03-31 12:42:21'),
 (117096,'z','Treasury',0,0,NULL,'2009-03-31 12:42:30'),
 (117103,'t','ESM',0,0,NULL,'2009-03-31 12:42:34'),
 (117105,'t','Microsoft Project',0,0,NULL,'2009-03-31 12:42:47'),
 (117104,'t','Microsoft Office',0,0,NULL,'2009-03-31 12:42:51'),
 (117106,'t','Microsoft Visio',0,0,NULL,'2009-03-31 12:42:57'),
 (117111,'p','Kendall Mclan',0,0,NULL,'2009-03-31 12:43:06'),
 (117112,'p','Kevin Piley',0,0,NULL,'2009-03-31 12:43:13'),
 (117140,'j','Analyst',0,0,NULL,'2009-03-31 12:49:08'),
 (117154,'z','ERM',0,0,NULL,'2009-03-31 12:51:23'),
 (117158,'t','C#',0,0,NULL,'2009-03-31 12:51:38'),
 (117162,'p','Kinna Smith',0,0,NULL,'2009-03-31 12:51:52'),
 (117163,'p','Jeff Maywood',0,0,NULL,'2009-03-31 12:51:59'),
 (117168,'i','Banking',0,0,NULL,'2009-03-31 12:52:08'),
 (117166,'a','Sales',0,0,NULL,'2009-03-31 12:52:19'),
 (117191,'w','Singapore',0,0,NULL,'2009-03-31 12:53:42'),
 (117214,'p','Danielle Oque',0,0,NULL,'2009-03-31 12:56:21'),
 (117215,'p','Tina Shah',0,0,NULL,'2009-03-31 12:56:32'),
 (117247,'j','Pricing Analyst',0,0,NULL,'2009-03-31 12:59:07'),
 (117245,'w','NYC',0,0,NULL,'2009-03-31 12:59:11'),
 (117367,'e','Warrants',0,0,NULL,'2009-04-01 23:46:59'),
 (117364,'e','Convertibles',0,0,NULL,'2009-04-01 23:46:31'),
 (117365,'e','Bonds',0,0,NULL,'2009-04-01 23:46:44'),
 (117368,'e','Options',0,0,NULL,'2009-04-01 23:47:06'),
 (117366,'e','Stocks',0,0,NULL,'2009-04-01 23:46:51'),
 (117511,'s','Java Script',0,0,NULL,'2009-04-01 23:54:36'),
 (117520,'e','CDOs',0,0,NULL,'2009-04-01 23:55:39'),
 (117533,'s','Java Test',0,0,NULL,'2009-04-01 23:56:45'),
 (117532,'s','Java Beans',0,0,NULL,'2009-04-01 23:56:35'),
 (117550,'s','C',0,0,NULL,'2009-04-01 23:57:33'),
 (117551,'s','C++',0,0,NULL,'2009-04-01 23:57:40'),
 (117705,'s','HTML',0,0,NULL,'2009-04-02 00:05:45'),
 (117721,'s','Flex',0,0,NULL,'2009-04-02 00:09:12'),
 (117722,'s','Adobe Live Cycle 8.1',0,0,NULL,'2009-04-02 00:09:33'),
 (122510,'e','CDO',0,0,NULL,'2009-04-15 13:37:02'),
 (122514,'s','Perl',0,0,NULL,'2009-04-15 13:40:24'),
 (122532,'p','Jeremy Robinson',0,0,NULL,'2009-04-15 14:24:36'),
 (122562,'z','CDO',0,0,NULL,'2009-04-15 14:26:24'),
 (122566,'a','Equity',0,0,NULL,'2009-04-15 14:26:51'),
 (122588,'w','Geneva',0,0,NULL,'2009-04-15 14:32:22'),
 (122586,'s','SQL',0,0,NULL,'2009-04-15 14:30:51'),
 (122765,'x','IT Associate',0,0,NULL,'2009-04-16 22:49:05'),
 (122766,'x','S&T Technology',0,0,NULL,'2009-04-16 23:47:25'),
 (122767,'x','Trading Technology',0,0,NULL,'2009-04-16 23:48:59'),
 (122779,'p','Mary Batchya',0,0,NULL,'2009-04-17 00:08:48'),
 (122780,'x','VP',0,0,NULL,'2009-04-17 10:03:55'),
 (122781,'x','Development Manager',0,0,NULL,'2009-04-17 12:45:45'),
 (122827,'a','Treasury',0,0,NULL,'2009-04-17 13:12:36'),
 (122863,'a','Equity Research',0,0,NULL,'2009-04-17 13:42:22'),
 (122864,'x','Database Administrator',0,0,NULL,'2009-04-17 13:46:48'),
 (122865,'a','Corporate IT',0,0,NULL,'2009-04-17 14:02:31'),
 (122866,'x','Programmer',0,0,NULL,'2009-04-17 15:15:11'),
 (122876,'x','Demo',0,0,NULL,'2009-04-18 09:08:23'),
 (122998,'x','Database Adminstrator',0,0,NULL,'2009-04-18 22:04:10'),
 (123163,'p','John Smith',0,0,NULL,'2009-04-18 22:15:42'),
 (123206,'j','Database Administrator',0,0,NULL,'2009-04-18 22:16:16'),
 (123203,'w','California',0,0,NULL,'2009-04-18 22:16:22'),
 (123411,'x','Data Architect',0,0,NULL,'2009-04-18 22:28:46'),
 (123452,'j','Data Architect',0,0,NULL,'2009-04-18 22:29:05'),
 (123864,'a','Finance',0,0,NULL,'2009-04-18 22:45:22'),
 (123917,'j','Recruiter',0,0,NULL,'2009-04-18 22:50:05'),
 (123916,'j','Generalist',0,0,NULL,'2009-04-18 22:50:01'),
 (123914,'e','Compensation',0,0,NULL,'2009-04-18 22:50:38'),
 (123913,'e','Benefits',0,0,NULL,'2009-04-18 22:50:34'),
 (123915,'e','Recruiting',0,0,NULL,'2009-04-18 22:50:44'),
 (123920,'a','Human Resources',0,0,NULL,'2009-04-18 22:49:54'),
 (124043,'x','Data Specialist',0,0,NULL,'2009-04-18 22:55:37'),
 (124039,'z','Taleo',0,0,NULL,'2009-04-18 22:54:34'),
 (124038,'z','Brassring',0,0,NULL,'2009-04-18 22:54:30'),
 (124047,'p','Tracy Ballone',0,0,NULL,'2009-04-18 22:56:06'),
 (124048,'p','James Lee',0,0,NULL,'2009-04-18 22:56:20'),
 (124051,'a','HRIS',0,0,NULL,'2009-04-18 22:57:27'),
 (124225,'j','Programming Manager',0,0,NULL,'2009-04-18 23:00:51'),
 (124227,'x','Technical Supervisor',0,0,NULL,'2009-04-18 23:02:59'),
 (124228,'x','Technology Manager',0,0,NULL,'2009-04-18 23:03:07'),
 (124268,'j','Trading Assistant',0,0,NULL,'2009-04-18 23:04:40'),
 (124269,'x','Trading Assistant',0,0,NULL,'2009-04-18 23:05:09'),
 (124353,'j','Investment Banker',0,0,NULL,'2009-04-18 23:08:06'),
 (124355,'x','M&A Developer',0,0,NULL,'2009-04-18 23:09:25'),
 (124528,'t','Cobol',0,0,NULL,'2009-04-18 23:15:58'),
 (124713,'j','Technology Manaer',0,0,NULL,'2009-04-18 23:20:37'),
 (124714,'j','Dataase Administrator',0,0,NULL,'2009-04-18 23:20:50'),
 (124715,'z','equities',0,0,NULL,'2009-04-18 23:21:27'),
 (124763,'x','Pricing Supervisor',0,0,NULL,'2009-04-18 23:25:41'),
 (124807,'p','Jackson Johnson',0,0,NULL,'2009-04-18 23:29:20'),
 (126197,'z','Munis',0,0,NULL,'2009-04-20 10:55:14'),
 (126485,'x','Project Supervisor',0,0,NULL,'2009-04-20 11:03:02'),
 (126565,'t','Mercury TestDirector',0,0,NULL,'2009-04-20 11:06:26'),
 (126611,'x','IT Consultant',0,0,NULL,'2009-04-20 11:07:39'),
 (126612,'a','Fixed Income',0,0,NULL,'2009-04-20 11:08:23'),
 (129776,'role_position','Senior Developer',0,0,NULL,'2009-04-21 07:11:37'),
 (131781,'s','Oracle',0,0,NULL,'2009-04-21 07:14:19'),
 (132278,'s','Segue Test',0,0,NULL,'2009-04-21 07:15:37');
/*!40000 ALTER TABLE `taxonomy` ENABLE KEYS */;


--
-- Definition of table `taxonomy_relation`
--

DROP TABLE IF EXISTS `taxonomy_relation`;
CREATE TABLE `taxonomy_relation` (
  `RELATION_ID` int(10) unsigned NOT NULL auto_increment,
  `TAXONOMY_ID` int(10) unsigned NOT NULL,
  `OBJECT_ID` int(10) unsigned NOT NULL,
  `RELATION_TYPE` char(30) NOT NULL,
  `LEVEL` int(10) unsigned default NULL,
  `ORDER_NO` smallint(5) unsigned default NULL,
  `RELEVANCE` smallint(5) unsigned default NULL,
  PRIMARY KEY  (`RELATION_ID`),
  KEY `Index_2` (`OBJECT_ID`,`TAXONOMY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `taxonomy_relation`
--

/*!40000 ALTER TABLE `taxonomy_relation` DISABLE KEYS */;
INSERT INTO `taxonomy_relation` (`RELATION_ID`,`TAXONOMY_ID`,`OBJECT_ID`,`RELATION_TYPE`,`LEVEL`,`ORDER_NO`,`RELEVANCE`) VALUES 
 (605,603,595,'s',4,1,NULL),
 (606,604,595,'s',2,2,NULL),
 (609,607,595,'l',2,1,NULL),
 (610,608,595,'l',2,2,NULL),
 (614,611,595,'w',0,1,NULL),
 (615,612,595,'w',0,2,NULL),
 (616,613,595,'w',0,3,NULL),
 (619,617,595,'j',0,1,NULL),
 (620,618,595,'j',0,2,NULL),
 (637,632,631,'z',0,1,NULL),
 (638,633,631,'z',0,2,NULL),
 (639,634,631,'z',0,3,NULL),
 (640,635,631,'z',0,4,NULL),
 (641,636,631,'z',0,5,NULL),
 (643,122998,631,'x',0,1,NULL),
 (649,644,631,'t',0,1,NULL),
 (650,645,631,'t',0,2,NULL),
 (651,646,631,'t',0,3,NULL),
 (652,647,631,'t',0,4,NULL),
 (653,648,631,'t',0,5,NULL),
 (658,654,631,'p',0,1,NULL),
 (659,655,631,'p',0,2,NULL),
 (660,656,631,'p',0,3,NULL),
 (661,657,631,'p',0,4,NULL),
 (663,662,631,'a',0,1,NULL),
 (665,664,631,'i',0,1,NULL),
 (794,792,790,'z',0,1,NULL),
 (795,632,790,'z',0,2,NULL),
 (796,793,790,'z',0,3,NULL),
 (798,797,790,'x',0,1,NULL),
 (800,799,790,'a',0,1,NULL),
 (801,664,790,'i',0,1,NULL),
 (817,814,790,'p',0,1,NULL),
 (818,815,790,'p',0,2,NULL),
 (819,816,790,'p',0,3,NULL),
 (825,823,790,'t',0,1,NULL),
 (826,644,790,'t',0,2,NULL),
 (827,824,790,'t',0,3,NULL),
 (828,0,0,'i',0,2,NULL),
 (851,849,847,'z',0,1,NULL),
 (852,850,847,'z',0,2,NULL),
 (854,648,847,'t',0,1,NULL),
 (855,853,847,'t',0,2,NULL),
 (856,656,847,'p',0,1,NULL),
 (857,654,847,'p',0,2,NULL),
 (859,858,847,'a',0,1,NULL),
 (860,664,847,'i',0,1,NULL),
 (861,0,0,'x',0,1,NULL),
 (888,793,886,'z',0,1,NULL),
 (889,632,886,'z',0,2,NULL),
 (891,890,886,'x',0,1,NULL),
 (892,824,886,'t',0,1,NULL),
 (895,893,886,'p',0,1,NULL),
 (896,657,886,'p',0,2,NULL),
 (897,894,886,'p',0,3,NULL),
 (899,898,886,'a',0,1,NULL),
 (900,664,886,'i',0,1,NULL),
 (901,0,0,'x',0,1,NULL),
 (902,0,0,'z',0,1,NULL),
 (903,0,0,'z',0,2,NULL),
 (904,0,0,'t',0,2,NULL),
 (905,0,0,'t',0,3,NULL),
 (906,0,0,'t',0,1,NULL),
 (907,0,0,'p',0,1,NULL),
 (908,0,0,'i',0,1,NULL),
 (909,0,0,'a',0,1,NULL),
 (1009,123163,923,'p',0,1,NULL),
 (1010,1008,923,'p',0,2,NULL),
 (1061,1060,922,'l',2,1,NULL),
 (1062,607,922,'l',2,2,NULL),
 (1072,607,1066,'l',2,1,NULL),
 (1074,611,1066,'w',0,1,NULL),
 (1075,1073,1066,'w',0,2,NULL),
 (1076,618,1066,'j',0,1,NULL),
 (1085,608,1066,'l',0,2,NULL),
 (1086,1084,1066,'l',3,3,NULL),
 (1087,0,0,'x',0,1,NULL),
 (1088,0,0,'z',0,1,NULL),
 (1089,0,0,'t',0,1,NULL),
 (1090,0,0,'t',0,2,NULL),
 (1091,0,0,'p',0,2,NULL),
 (1092,0,0,'p',0,1,NULL),
 (1120,607,1117,'l',2,1,NULL),
 (1121,1060,1117,'l',3,2,NULL),
 (1123,611,1117,'w',0,1,NULL),
 (1124,1073,1117,'w',0,2,NULL),
 (1125,1122,1117,'w',0,3,NULL),
 (1127,1126,1117,'j',0,1,NULL),
 (1128,0,0,'x',0,1,NULL),
 (1129,0,0,'z',0,1,NULL),
 (1130,0,0,'z',0,3,NULL),
 (1131,0,0,'z',0,2,NULL),
 (1132,0,0,'z',0,5,NULL),
 (1133,0,0,'z',0,4,NULL),
 (1134,0,0,'t',0,1,NULL),
 (1135,0,0,'t',0,2,NULL),
 (1136,0,0,'p',0,1,NULL),
 (1137,0,0,'p',0,2,NULL),
 (1138,0,0,'i',0,1,NULL),
 (1139,0,0,'a',0,1,NULL),
 (1190,607,1189,'l',2,1,NULL),
 (1199,1198,1189,'w',0,1,NULL),
 (1202,123452,1189,'j',0,1,NULL),
 (1203,618,1189,'j',0,2,NULL),
 (1204,1201,1189,'j',0,3,NULL),
 (1213,123411,1210,'x',0,1,NULL),
 (1214,1007,1210,'p',0,1,NULL),
 (1215,655,1210,'p',0,2,NULL),
 (1217,1216,1210,'a',0,1,NULL),
 (1218,664,1210,'i',0,1,NULL),
 (1236,607,1234,'l',2,1,NULL),
 (1237,1235,1234,'l',2,2,NULL),
 (1238,1073,1234,'w',0,1,NULL),
 (1240,1239,1234,'j',0,1,NULL),
 (1254,1288,1252,'x',0,1,NULL),
 (1262,1259,1252,'z',0,1,NULL),
 (1263,1260,1252,'z',0,2,NULL),
 (1264,1261,1252,'z',0,3,NULL),
 (1266,824,1252,'t',0,1,NULL),
 (1267,1265,1252,'t',0,2,NULL),
 (1269,1268,1252,'p',0,1,NULL),
 (1284,632,1282,'z',0,1,NULL),
 (1285,634,1282,'z',0,2,NULL),
 (1286,635,1282,'z',0,3,NULL),
 (1287,636,1282,'z',0,4,NULL),
 (1289,1288,1282,'x',0,1,NULL),
 (1290,645,1282,'t',0,1,NULL),
 (1291,644,1282,'t',0,2,NULL),
 (1294,1292,1282,'p',0,1,NULL),
 (1295,1293,1282,'p',0,2,NULL),
 (1297,858,1282,'a',0,1,NULL),
 (1298,664,1282,'i',0,1,NULL),
 (1322,607,1321,'l',0,1,NULL),
 (1323,608,1321,'l',2,2,NULL),
 (1325,611,1321,'w',0,1,NULL),
 (1326,1324,1321,'w',0,2,NULL),
 (1327,123916,1321,'j',0,1,NULL),
 (1345,850,1342,'z',0,1,NULL),
 (1346,635,1342,'z',0,2,NULL),
 (1347,636,1342,'z',0,3,NULL),
 (1348,1344,1342,'z',0,4,NULL),
 (1349,632,1342,'z',0,5,NULL),
 (1351,124043,1342,'x',0,1,NULL),
 (1352,644,1342,'t',0,1,NULL),
 (1353,645,1342,'t',0,2,NULL),
 (1356,1354,1342,'p',0,1,NULL),
 (1357,1355,1342,'p',0,2,NULL),
 (1359,123920,1342,'a',0,1,NULL),
 (1361,1360,1342,'i',0,1,NULL),
 (1394,1118,1384,'l',0,1,NULL),
 (1395,607,1384,'l',2,2,NULL),
 (1396,1393,1384,'l',2,3,NULL),
 (1397,611,1384,'w',0,1,NULL),
 (1398,1073,1384,'w',0,2,NULL),
 (1400,116823,1384,'j',0,1,NULL),
 (1415,1412,1385,'z',0,1,NULL),
 (1416,1413,1385,'z',0,2,NULL),
 (1417,636,1385,'z',0,3,NULL),
 (1418,1414,1385,'z',0,4,NULL),
 (1419,632,1385,'z',0,5,NULL),
 (1420,124228,1385,'x',0,1,NULL),
 (1421,644,1385,'t',0,1,NULL),
 (1422,645,1385,'t',0,2,NULL),
 (1424,1423,1385,'p',0,1,NULL),
 (1425,1354,1385,'p',0,2,NULL),
 (1427,122566,1385,'a',0,1,NULL),
 (1428,664,1385,'i',0,1,NULL),
 (1453,793,1450,'z',0,1,NULL),
 (1454,632,1450,'z',0,2,NULL),
 (1456,124227,1450,'x',0,1,NULL),
 (1457,824,1450,'t',0,1,NULL),
 (1458,1007,1450,'p',0,1,NULL),
 (1459,657,1450,'p',0,2,NULL),
 (1460,894,1450,'p',0,3,NULL),
 (1461,898,1450,'a',0,1,NULL),
 (1462,664,1450,'i',0,1,NULL),
 (1492,607,1486,'l',0,1,NULL),
 (1493,1073,1486,'w',0,1,NULL),
 (1495,124268,1486,'j',0,1,NULL),
 (1505,1503,1501,'z',0,1,NULL),
 (1506,849,1501,'z',0,2,NULL),
 (1507,1504,1501,'z',0,3,NULL),
 (1508,1412,1501,'z',0,4,NULL),
 (1510,124269,1501,'x',0,1,NULL),
 (1513,1511,1501,'t',0,1,NULL),
 (1514,1512,1501,'t',0,2,NULL),
 (1516,1515,1501,'a',0,1,NULL),
 (1517,664,1501,'i',0,1,NULL),
 (1518,0,604,'j',0,1,NULL),
 (1519,0,604,'j',0,2,0),
 (1520,0,604,'j',0,2,0),
 (1521,0,604,'j',0,2,0),
 (1522,0,604,'j',0,2,0),
 (1523,0,604,'j',0,2,0),
 (1524,0,604,'j',0,2,0),
 (1525,0,604,'j',0,2,0),
 (1526,0,604,'j',0,2,0),
 (1527,0,604,'j',0,2,0),
 (1528,0,604,'j',0,2,0),
 (1529,0,604,'j',0,2,0),
 (8851,617,823,'j',0,1,0),
 (9352,617,823,'j',0,1,0),
 (9854,617,823,'j',0,1,0),
 (10355,617,823,'j',0,1,0),
 (10856,129776,823,'role_position',0,1,0),
 (11858,1073,823,'role_location',0,1,0),
 (17368,603,17367,'role_skill',0,1,0),
 (18870,603,17869,'role_skill',0,1,0),
 (27878,603,823,'role_skill',1,1,0),
 (105545,105544,105543,'role_skill',0,1,0),
 (106046,106045,106044,'role_position',0,1,0),
 (106546,106045,106545,'role_position',0,1,0),
 (106548,106547,106545,'role_skill',0,1,0),
 (107047,107046,106545,'role_skill',0,2,0),
 (107560,1201,107559,'role_position',0,1,0),
 (107561,106045,107559,'role_position',0,2,0),
 (107562,601,107559,'role_expertize',0,1,0),
 (107563,106547,107559,'role_skill',0,1,0),
 (112561,1073,111560,'role_location',0,1,0),
 (113561,617,111560,'role_position',0,1,0),
 (113562,1201,111560,'role_position',0,2,0),
 (116065,617,116064,'role_position',0,1,0),
 (116066,1201,116064,'role_position',0,2,0),
 (116068,105544,116064,'role_skill',0,1,0),
 (116069,116067,116064,'role_skill',0,2,0),
 (116569,116568,116565,'l',2,1,0),
 (116570,607,116565,'l',2,2,0),
 (116571,1073,116565,'w',0,1,0),
 (116573,124353,116565,'j',0,1,0),
 (116575,116574,116566,'z',0,1,0),
 (116577,124355,116566,'x',0,1,0),
 (116579,1511,116566,'t',0,1,0),
 (116580,1512,116566,'t',0,2,0),
 (116581,116578,116566,'t',0,3,0),
 (116583,656,116566,'p',0,1,0),
 (116584,654,116566,'p',0,2,0),
 (116585,116582,116566,'p',0,3,0),
 (116586,799,116566,'a',0,1,0),
 (116587,664,116566,'i',0,1,0),
 (116762,116761,116760,'l',0,1,0),
 (116763,1118,116760,'l',2,2,0),
 (116764,607,116760,'l',2,3,0),
 (116765,1073,116760,'w',0,1,0),
 (116767,116766,116760,'j',0,1,0),
 (116786,116783,116781,'z',0,1,0),
 (116787,116784,116781,'z',0,2,0),
 (116788,116785,116781,'z',0,3,0),
 (116790,116789,116781,'x',0,1,0),
 (116793,116791,116781,'t',0,1,0),
 (116794,116792,116781,'t',0,2,0),
 (116795,1511,116781,'t',0,3,0),
 (116796,656,116781,'p',0,1,0),
 (116797,654,116781,'p',0,2,0),
 (116821,607,116820,'l',0,1,0),
 (116822,1073,116820,'w',0,1,0),
 (116824,116823,116820,'j',0,1,0),
 (116841,116837,116835,'z',0,1,0),
 (116842,116838,116835,'z',0,2,0),
 (116843,116839,116835,'z',0,3,0),
 (116844,116840,116835,'z',0,4,0),
 (116846,116845,116835,'x',0,1,0),
 (116847,644,116835,'t',0,1,0),
 (116848,853,116835,'t',0,2,0),
 (116851,116849,116835,'p',0,1,0),
 (116852,116850,116835,'p',0,2,0),
 (116853,858,116835,'a',0,1,0),
 (116854,664,116835,'i',0,1,0),
 (116882,116881,116880,'l',0,1,0),
 (116883,607,116880,'l',2,2,0),
 (116884,1073,116880,'w',0,1,0),
 (116887,124713,116880,'j',0,1,0),
 (116888,124714,116880,'j',0,2,0),
 (116905,124228,116901,'x',0,1,0),
 (116907,116906,116901,'p',0,1,0),
 (116909,116908,116901,'a',0,1,0),
 (116910,664,116901,'i',0,1,0),
 (116927,116926,116925,'l',2,1,0),
 (116928,607,116925,'l',0,2,0),
 (116930,1073,116925,'w',0,1,0),
 (116931,116929,116925,'w',0,2,0),
 (116934,116932,116925,'j',0,1,0),
 (116935,116933,116925,'j',0,2,0),
 (116954,635,116949,'z',0,1,0),
 (116955,116952,116949,'z',0,2,0),
 (116956,116953,116949,'z',0,3,0),
 (116958,116957,116949,'x',0,1,0),
 (116959,116906,116949,'p',0,1,0),
 (116960,116849,116949,'p',0,2,0),
 (116962,116961,116949,'a',0,1,0),
 (116963,664,116949,'i',0,1,0),
 (116983,607,116982,'l',0,1,0),
 (116985,116984,116982,'j',0,1,0),
 (116998,116997,116994,'x',0,1,0),
 (117000,116999,116994,'p',0,1,0),
 (117001,858,116994,'a',0,1,0),
 (117002,664,116994,'i',0,1,0),
 (117016,607,117015,'l',0,1,0),
 (117017,116761,117015,'l',0,2,0),
 (117018,1060,117015,'l',4,3,0),
 (117019,1073,117015,'w',0,1,0),
 (117020,611,117015,'w',0,2,0),
 (117021,1239,117015,'j',0,1,0),
 (117043,618,117015,'j',0,2,0),
 (117047,117044,117039,'z',0,1,0),
 (117048,117045,117039,'z',0,2,0),
 (117049,117046,117039,'z',0,3,0),
 (117050,1288,117039,'x',0,1,0),
 (117052,117051,117039,'t',0,1,0),
 (117053,824,117039,'t',0,2,0),
 (117054,644,117039,'t',0,3,0),
 (117058,117055,117039,'p',0,1,0),
 (117059,117056,117039,'p',0,2,0),
 (117060,117057,117039,'p',0,3,0),
 (117061,898,117039,'a',0,1,0),
 (117062,664,117039,'i',0,1,0),
 (117097,116952,117093,'z',0,1,0),
 (117098,116953,117093,'z',0,2,0),
 (117099,117095,117093,'z',0,3,0),
 (117100,117096,117093,'z',0,4,0),
 (117102,126485,117093,'x',0,1,0),
 (117107,117103,117093,'t',0,1,0),
 (117108,117104,117093,'t',0,2,0),
 (117109,117105,117093,'t',0,3,0),
 (117110,117106,117093,'t',0,4,0),
 (117113,117111,117093,'p',0,1,0),
 (117114,117112,117093,'p',0,2,0),
 (117115,117057,117093,'p',0,3,0),
 (117135,607,838,'l',0,1,0),
 (117136,116926,838,'l',3,2,0),
 (117137,611,838,'w',0,1,0),
 (117138,1198,838,'w',0,2,0),
 (117139,1073,838,'w',0,3,0),
 (117141,618,838,'j',0,1,0),
 (117155,116838,117153,'z',0,1,0),
 (117156,117154,117153,'z',0,2,0),
 (117157,117101,117153,'x',0,1,0),
 (117159,644,117153,'t',0,1,0),
 (117160,823,117153,'t',0,2,0),
 (117161,117158,117153,'t',0,3,0),
 (117164,117162,117153,'p',0,1,0),
 (117165,117163,117153,'p',0,2,0),
 (117167,116961,117153,'a',0,1,0),
 (117169,664,117153,'i',0,1,0),
 (117190,607,117189,'l',2,1,0),
 (117192,117191,117189,'w',0,1,0),
 (117193,1122,117189,'w',0,2,0),
 (117194,618,117189,'j',0,1,0),
 (117211,632,117207,'z',0,1,0),
 (117212,636,117207,'z',0,2,0),
 (117213,117101,117207,'x',0,1,0),
 (117216,117214,117207,'p',0,1,0),
 (117217,117215,117207,'p',0,2,0),
 (117218,799,117207,'a',0,1,0),
 (117219,664,117207,'i',0,1,0),
 (117244,1118,117240,'l',2,1,0),
 (117246,1073,117240,'w',0,1,0),
 (117248,117247,117240,'j',0,1,0),
 (117249,126611,117241,'x',0,1,0),
 (117369,117364,595,'e',0,1,0),
 (117370,117365,595,'e',0,2,0),
 (117372,117367,595,'e',0,3,0),
 (117499,117364,838,'e',0,1,0),
 (117512,105544,838,'s',4,1,0),
 (117513,117511,838,'s',4,2,0),
 (117514,603,838,'s',3,3,0),
 (117521,117520,922,'e',0,1,0),
 (117522,117365,922,'e',0,2,0),
 (117523,117366,922,'e',0,3,0),
 (117534,603,922,'s',3,1,0),
 (117535,117532,922,'s',3,2,0),
 (117536,117533,922,'s',2,3,0),
 (117541,117520,1066,'e',0,1,0),
 (117542,117366,1066,'e',0,2,0),
 (117543,117364,1066,'e',0,3,0),
 (117552,117550,1066,'s',4,1,0),
 (117553,117551,1066,'s',4,2,0),
 (117554,604,1066,'s',4,3,0),
 (117559,117368,1117,'e',0,1,0),
 (117560,117367,1117,'e',0,2,0),
 (117565,105544,1117,'s',2,1,0),
 (117566,603,1117,'s',3,2,0),
 (117571,117364,1189,'e',0,1,0),
 (117572,601,1189,'e',0,2,0),
 (117573,604,1189,'s',4,1,0),
 (117574,117551,1189,'s',2,2,0),
 (117583,117520,1234,'e',0,1,0),
 (117584,601,1234,'e',0,2,0),
 (117589,603,1234,'s',2,1,0),
 (117592,601,1486,'e',0,1,0),
 (117593,117364,1486,'e',0,2,0),
 (117594,117368,1486,'e',0,3,0),
 (117595,105544,1486,'s',2,1,0),
 (117596,117511,1486,'s',3,2,0),
 (117607,601,116565,'e',0,1,0),
 (117608,117365,116565,'e',0,2,0),
 (117609,604,116565,'s',3,1,0),
 (117610,117551,116565,'s',3,2,0),
 (117611,603,116565,'s',3,3,0),
 (117622,117364,116760,'e',0,1,0),
 (117623,601,116760,'e',0,2,0),
 (117624,117365,116760,'e',0,3,0),
 (117625,105544,116760,'s',2,1,0),
 (117626,603,116760,'s',2,2,0),
 (117637,117364,116820,'e',0,1,0),
 (117638,601,116820,'e',0,2,0),
 (117639,117368,116820,'e',0,3,0),
 (117640,105544,116820,'s',2,1,0),
 (117641,603,116820,'s',2,2,0),
 (117652,601,116880,'e',0,1,0),
 (117653,117365,116880,'e',0,2,0),
 (117654,117368,116880,'e',0,3,0),
 (117655,604,116880,'s',4,1,0),
 (117664,601,116925,'e',0,1,0),
 (117665,117365,116925,'e',0,2,0),
 (117666,117533,116925,'s',3,1,0),
 (117667,105544,116925,'s',3,2,0),
 (117676,117365,116982,'e',0,1,0),
 (117677,601,116982,'e',0,2,0),
 (117678,105544,116982,'s',4,1,0),
 (117685,117365,117015,'e',0,1,0),
 (117686,117368,117015,'e',0,2,0),
 (117687,604,117015,'s',2,1,0),
 (117694,117364,117189,'e',0,1,0),
 (117695,117368,117189,'e',0,2,0),
 (117696,604,117189,'s',2,1,0),
 (117703,117365,117240,'e',0,1,0),
 (117704,117368,117240,'e',0,2,0),
 (117706,117511,117240,'s',3,1,0),
 (117707,117705,117240,'s',2,2,0),
 (117708,603,117240,'s',3,3,0),
 (117719,117365,1384,'e',0,1,0),
 (117720,117368,1384,'e',0,2,0),
 (117723,117721,1384,'s',4,1,0),
 (117724,105544,1384,'s',4,2,0),
 (117725,117722,1384,'s',4,3,0),
 (118412,1201,118411,'role_position',0,1,0),
 (118413,117721,118411,'role_skill',0,1,0),
 (118414,0,118411,'role_location',0,1,0),
 (118913,617,118912,'role_position',0,1,0),
 (118914,1073,118912,'role_location',0,1,0),
 (118915,601,118912,'role_expertize',1,1,0),
 (118916,117368,118912,'role_expertize',1,2,0),
 (118917,117365,118912,'role_expertize',1,3,0),
 (118918,117364,118912,'role_expertize',1,4,0),
 (118919,117721,118912,'role_skill',1,1,0),
 (118920,117533,118912,'role_skill',1,2,0),
 (119918,617,119917,'role_position',0,1,0),
 (119919,611,119917,'role_location',0,1,0),
 (119920,117533,119917,'role_skill',0,1,0),
 (122501,655,1144,'p',0,1,0),
 (122502,1007,1144,'p',0,2,0),
 (122511,123913,1321,'e',0,1,0),
 (122512,123914,1321,'e',0,2,0),
 (122513,123915,1321,'e',0,3,0),
 (122515,105544,1321,'s',4,1,0),
 (122516,122514,1321,'s',4,2,0),
 (122527,632,122525,'z',0,1,0),
 (122528,793,122525,'z',0,2,0),
 (122529,1288,122525,'x',0,1,0),
 (122530,644,122525,'t',0,1,0),
 (122531,117105,122525,'t',0,2,0),
 (122533,1423,122525,'p',0,1,0),
 (122534,1007,122525,'p',0,2,0),
 (122535,122532,122525,'p',0,3,0),
 (122536,116961,122525,'a',0,1,0),
 (122537,664,122525,'i',0,1,0),
 (122563,122562,117241,'z',0,1,0),
 (122564,1504,117241,'z',0,2,0),
 (122565,645,117241,'t',0,1,0),
 (122567,126612,117241,'a',0,1,0),
 (122568,664,117241,'i',0,1,0),
 (122766,122765,1144,'x',0,1,0),
 (122769,1288,1090,'x',0,1,0),
 (122774,850,122772,'z',0,1,0),
 (122775,793,122772,'z',0,2,0),
 (122776,116957,122772,'x',0,1,0),
 (122777,645,122772,'t',0,1,0),
 (122778,644,122772,'t',0,2,0),
 (122782,664,122772,'i',0,1,0),
 (122823,797,923,'x',0,1,0),
 (122824,858,923,'a',0,1,0),
 (122825,664,923,'i',0,1,0),
 (122828,122827,925,'a',0,1,0),
 (122829,664,925,'i',0,1,0),
 (122845,122566,923,'a',0,2,0),
 (122848,122562,1090,'z',0,1,0),
 (122849,850,1090,'z',0,2,0),
 (122850,793,1090,'z',0,3,0),
 (122851,645,1090,'t',0,1,0),
 (122852,823,1090,'t',0,2,0),
 (122864,122863,1144,'a',0,1,0),
 (122865,664,1144,'i',0,1,0),
 (122997,122866,847,'x',0,1,0),
 (123204,123203,922,'w',0,1,0),
 (123205,611,922,'w',0,2,0),
 (123207,617,922,'j',0,1,0),
 (123208,123206,922,'j',0,2,0),
 (123493,1073,1189,'w',0,2,0),
 (123575,645,1210,'t',0,1,0),
 (123576,644,1210,'t',0,2,0),
 (123577,853,1210,'t',0,3,0),
 (123861,618,1234,'j',0,2,0),
 (123862,122865,1282,'a',0,2,0),
 (123863,1354,1252,'p',0,2,0),
 (123865,122827,1252,'a',0,1,0),
 (123866,123864,1252,'a',0,2,0),
 (123867,664,1252,'i',0,1,0),
 (123918,123917,1321,'j',0,2,0),
 (123919,116766,1321,'j',0,3,0),
 (123920,0,0,'x',0,1,0),
 (124040,124038,124037,'z',0,1,0),
 (124041,124039,124037,'z',0,2,0),
 (124042,116838,124037,'z',0,3,0),
 (124044,1212,124037,'x',0,1,0),
 (124045,824,124037,'t',0,1,0),
 (124046,644,124037,'t',0,2,0),
 (124049,124047,124037,'p',0,1,0),
 (124050,124048,124037,'p',0,2,0),
 (124052,123920,124037,'a',0,1,0),
 (124053,124051,124037,'a',0,2,0),
 (124054,117168,124037,'i',0,1,0),
 (124055,664,124037,'i',0,2,0),
 (124226,124225,1384,'j',0,2,0),
 (124267,116926,1486,'l',2,2,0),
 (124270,1292,1501,'p',0,1,0),
 (124271,124047,1501,'p',0,2,0),
 (124354,617,116565,'j',0,2,0),
 (124477,617,116820,'j',0,2,0),
 (124479,0,0,'x',0,1,0),
 (124525,122562,124475,'z',0,1,0),
 (124526,793,124475,'z',0,2,0),
 (124527,850,124475,'z',0,3,0),
 (124529,824,124475,'t',0,1,0),
 (124530,644,124475,'t',0,2,0),
 (124531,124528,124475,'t',0,3,0),
 (124585,1412,923,'z',0,1,0),
 (124586,793,923,'z',0,2,0),
 (124629,123163,124475,'p',0,1,0),
 (124671,797,124475,'x',0,1,0),
 (124716,124715,116901,'z',0,1,0),
 (124717,793,116901,'z',0,2,0),
 (124718,1504,116901,'z',0,3,0),
 (124719,644,116901,'t',0,1,0),
 (124720,823,116901,'t',0,2,0),
 (124764,124528,116949,'t',0,1,0),
 (124765,117158,116949,'t',0,2,0),
 (124808,124807,122772,'p',0,1,0),
 (125672,1007,1090,'p',0,1,0),
 (126198,793,116566,'z',0,2,0),
 (126199,126197,116566,'z',0,3,0),
 (126361,117551,116880,'s',4,2,0),
 (126362,116067,116880,'s',3,3,0),
 (126566,645,117207,'t',0,1,0),
 (126567,117105,117207,'t',0,2,0),
 (126568,126565,117207,'t',0,3,0),
 (126907,1288,126863,'x',0,1,0),
 (127707,116997,127082,'x',0,1,0),
 (128276,601,823,'role_expertize',1,1,0),
 (128277,117365,823,'role_expertize',1,2,0),
 (128278,117511,823,'role_skill',1,2,0),
 (131779,117365,826,'role_expertize',1,1,0),
 (131780,601,826,'role_expertize',1,2,0),
 (131782,131781,826,'role_skill',1,1,0),
 (132279,132278,2835,'role_skill',1,1,0);
/*!40000 ALTER TABLE `taxonomy_relation` ENABLE KEYS */;


--
-- Definition of function `fn_gen_keys`
--

DROP FUNCTION IF EXISTS `fn_gen_keys`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */ $$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_gen_keys`(keysCount int) RETURNS int(11)
BEGIN
DECLARE currentKey int default -1;
DECLARE keysCursor CURSOR FOR
  select seq from sequence where seq_type='A' for update;
OPEN keysCursor;
  FETCH keysCursor INTO currentKey;
CLOSE keysCursor;
update sequence set seq=(currentKey + keysCount) where seq_type='A';
return currentKey+1;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
