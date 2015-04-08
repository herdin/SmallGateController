-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.0.17-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win32
-- HeidiSQL 버전:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- smallgatecontroller 의 데이터베이스 구조 덤핑
DROP DATABASE IF EXISTS `smallgatecontroller`;
CREATE DATABASE IF NOT EXISTS `smallgatecontroller` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `smallgatecontroller`;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. accesshistory
DROP TABLE IF EXISTS `accesshistory`;
CREATE TABLE IF NOT EXISTS `accesshistory` (
  `CARD_ID` varchar(50) DEFAULT NULL,
  `GATE_ID` varchar(50) DEFAULT NULL,
  `ACCESS_DATE` varchar(14) DEFAULT NULL,
  KEY `FK_ACCESSHISTORY_card` (`CARD_ID`),
  KEY `FK_ACCESSHISTORY_gate` (`GATE_ID`),
  CONSTRAINT `FK_ACCESSHISTORY_card` FOREIGN KEY (`CARD_ID`) REFERENCES `card` (`CARD_ID`),
  CONSTRAINT `FK_ACCESSHISTORY_gate` FOREIGN KEY (`GATE_ID`) REFERENCES `gate` (`GATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='접근이력';

-- Dumping data for table smallgatecontroller.accesshistory: ~0 rows (대략적)
DELETE FROM `accesshistory`;
/*!40000 ALTER TABLE `accesshistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `accesshistory` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. area
DROP TABLE IF EXISTS `area`;
CREATE TABLE IF NOT EXISTS `area` (
  `AREA_ID` varchar(50) NOT NULL,
  `AREA_DESC` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AREA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='지역';

-- Dumping data for table smallgatecontroller.area: ~0 rows (대략적)
DELETE FROM `area`;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
/*!40000 ALTER TABLE `area` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. card
DROP TABLE IF EXISTS `card`;
CREATE TABLE IF NOT EXISTS `card` (
  `CARD_ID` varchar(50) NOT NULL,
  `CARD_DESC` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CARD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카드';

-- Dumping data for table smallgatecontroller.card: ~25 rows (대략적)
DELETE FROM `card`;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` (`CARD_ID`, `CARD_DESC`) VALUES
	('0101', NULL),
	('0102', NULL),
	('0103', NULL),
	('20150330181211', 'card desc'),
	('20150330181219', 'card desc'),
	('20150330181226', 'card desc'),
	('20150330181228', 'card desc'),
	('20150402205156', 'card desc'),
	('20150402205225', 'card desc'),
	('20150402205246', 'card desc'),
	('20150402205333', 'card desc'),
	('20150402205348', 'card desc'),
	('20150403140535', 'card desc'),
	('20150403140708', 'card desc'),
	('20150403140711', 'card desc'),
	('20150403140713', 'card desc'),
	('20150403142301', 'card desc'),
	('20150403142313', 'card desc'),
	('20150403142341', 'card desc'),
	('20150403142515', 'card desc'),
	('20150403143615', 'card desc'),
	('20150403143620', 'card desc'),
	('20150403143819', 'card desc'),
	('20150403144048', 'card desc'),
	('20150403144146', 'card desc');
/*!40000 ALTER TABLE `card` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. cardgroup
DROP TABLE IF EXISTS `cardgroup`;
CREATE TABLE IF NOT EXISTS `cardgroup` (
  `CARD_ID` varchar(50) NOT NULL,
  `GROUP_ID` varchar(50) NOT NULL,
  PRIMARY KEY (`CARD_ID`,`GROUP_ID`),
  KEY `FK_CARDGROUP_group` (`GROUP_ID`),
  CONSTRAINT `FK_CARDGROUP_card` FOREIGN KEY (`CARD_ID`) REFERENCES `card` (`CARD_ID`),
  CONSTRAINT `FK_CARDGROUP_group` FOREIGN KEY (`GROUP_ID`) REFERENCES `group` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카드 - 그룹 매핑';

-- Dumping data for table smallgatecontroller.cardgroup: ~0 rows (대략적)
DELETE FROM `cardgroup`;
/*!40000 ALTER TABLE `cardgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `cardgroup` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. gate
DROP TABLE IF EXISTS `gate`;
CREATE TABLE IF NOT EXISTS `gate` (
  `GATE_ID` varchar(50) NOT NULL,
  `GATE_DESC` varchar(50) DEFAULT NULL,
  `GATE_PINGED_DATE` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`GATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게이트';

-- Dumping data for table smallgatecontroller.gate: ~0 rows (대략적)
DELETE FROM `gate`;
/*!40000 ALTER TABLE `gate` DISABLE KEYS */;
/*!40000 ALTER TABLE `gate` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. gatearea
DROP TABLE IF EXISTS `gatearea`;
CREATE TABLE IF NOT EXISTS `gatearea` (
  `GATE_ID` varchar(50) NOT NULL,
  `AREA_ID` varchar(50) NOT NULL,
  PRIMARY KEY (`GATE_ID`,`AREA_ID`),
  KEY `FK_GATEAREA_area` (`AREA_ID`),
  CONSTRAINT `FK_GATEAREA_area` FOREIGN KEY (`AREA_ID`) REFERENCES `area` (`AREA_ID`),
  CONSTRAINT `FK_GATEAREA_gate` FOREIGN KEY (`GATE_ID`) REFERENCES `gate` (`GATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게이트 - 지역 매핑';

-- Dumping data for table smallgatecontroller.gatearea: ~0 rows (대략적)
DELETE FROM `gatearea`;
/*!40000 ALTER TABLE `gatearea` DISABLE KEYS */;
/*!40000 ALTER TABLE `gatearea` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. group
DROP TABLE IF EXISTS `group`;
CREATE TABLE IF NOT EXISTS `group` (
  `GROUP_ID` varchar(50) NOT NULL,
  `GROUP_DESC` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='그룹';

-- Dumping data for table smallgatecontroller.group: ~0 rows (대략적)
DELETE FROM `group`;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
/*!40000 ALTER TABLE `group` ENABLE KEYS */;


-- 테이블 smallgatecontroller의 구조를 덤프합니다. grouparea
DROP TABLE IF EXISTS `grouparea`;
CREATE TABLE IF NOT EXISTS `grouparea` (
  `GROUP_ID` varchar(50) NOT NULL,
  `AREA_ID` varchar(50) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`AREA_ID`),
  KEY `FK_GROUPAREA_area` (`AREA_ID`),
  CONSTRAINT `FK_GROUPAREA_area` FOREIGN KEY (`AREA_ID`) REFERENCES `area` (`AREA_ID`),
  CONSTRAINT `FK_GROUPAREA_group` FOREIGN KEY (`GROUP_ID`) REFERENCES `group` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='그룹 - 지역 매핑';

-- Dumping data for table smallgatecontroller.grouparea: ~0 rows (대략적)
DELETE FROM `grouparea`;
/*!40000 ALTER TABLE `grouparea` DISABLE KEYS */;
/*!40000 ALTER TABLE `grouparea` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
