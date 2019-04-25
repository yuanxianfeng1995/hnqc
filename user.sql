/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.7.25 : Database - user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `user`;

/*Table structure for table `banji` */

DROP TABLE IF EXISTS `banji`;

CREATE TABLE `banji` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `word` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `banji` */

/*Table structure for table `billentry` */

DROP TABLE IF EXISTS `billentry`;

CREATE TABLE `billentry` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `number` int(11) DEFAULT NULL COMMENT '页小计数量',
  `price` double DEFAULT NULL COMMENT '单价',
  `money` double DEFAULT NULL COMMENT '页小计金额',
  `manufacturer` varchar(45) DEFAULT NULL COMMENT '单位',
  `addr` varchar(300) DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `conID` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `commo` (`conID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `billentry` */

/*Table structure for table `commodity` */

DROP TABLE IF EXISTS `commodity`;

CREATE TABLE `commodity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `billentryId` int(11) unsigned DEFAULT NULL,
  `outboundId` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bullentry` (`billentryId`),
  KEY `outbound` (`outboundId`),
  CONSTRAINT `bullentry` FOREIGN KEY (`billentryId`) REFERENCES `billentry` (`id`),
  CONSTRAINT `outbound` FOREIGN KEY (`outboundId`) REFERENCES `outbound` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `commodity` */

insert  into `commodity`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`purchased_date`,`remark`,`billentryId`,`outboundId`) values (1,'2','asd',21,1,21,'撒的',NULL,NULL,NULL,NULL),(2,'no','name',1,15.2,NULL,'manufacturer','2018-02-04','remark',NULL,NULL),(3,'ad','ad',0,0,NULL,'爱迪生','2019-04-25','阿萨德',NULL,NULL),(4,'爱迪生','阿萨德',0,0,NULL,'撒的','2019-04-25','',NULL,NULL),(5,'1','11',1,0,NULL,'1','2019-04-25','1',NULL,NULL);

/*Table structure for table `maintain` */

DROP TABLE IF EXISTS `maintain`;

CREATE TABLE `maintain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `license` varchar(10) DEFAULT NULL COMMENT '车牌号码',
  `mileage` varchar(10) DEFAULT NULL COMMENT '里程',
  `CompulsoryInsurance` date DEFAULT NULL COMMENT '交强险到期时间',
  `content` varchar(300) DEFAULT NULL COMMENT '保养内容',
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`userID`),
  KEY `usr FOREIGN KEY` (`userID`),
  CONSTRAINT `usr FOREIGN KEY` FOREIGN KEY (`userID`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `maintain` */

/*Table structure for table `outbound` */

DROP TABLE IF EXISTS `outbound`;

CREATE TABLE `outbound` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `number` int(11) DEFAULT NULL COMMENT '页小计数量',
  `price` double DEFAULT NULL COMMENT '单价',
  `money` double DEFAULT NULL COMMENT '页小计金额',
  `manufacturer` varchar(45) DEFAULT NULL COMMENT '单位',
  `addr` varchar(300) DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `conID` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `commodity` (`conID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `outbound` */

insert  into `outbound`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`addr`,`purchased_date`,`making`,`Handle`,`remark`,`conID`) values (1,'xsf','正则',12,2,24,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `xuehao` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  `classid` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FOREIGN KEY` (`classid`),
  CONSTRAINT `FOREIGN KEY` FOREIGN KEY (`classid`) REFERENCES `banji` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `student` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`sex`,`age`,`birthday`,`remark`,`phone`) values (1,'阿道夫吓死','男','20','2019-04-20','撒地方反复','1563132');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
