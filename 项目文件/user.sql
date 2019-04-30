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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;

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
  `purchasedDate` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `commodityId` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `billentry` */

/*Table structure for table `commodity` */

DROP TABLE IF EXISTS `commodity`;

CREATE TABLE `commodity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `price` double DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `commodity` */

insert  into `commodity`(`id`,`no`,`name`,`price`,`manufacturer`,`purchased_date`,`remark`) values (1,'xads啊啊','萨达',1,'阿萨德','2019-04-29',''),(2,'xx','adasd',1,'xz','2019-04-30','xzc');

/*Table structure for table `commoditylist` */

DROP TABLE IF EXISTS `commoditylist`;

CREATE TABLE `commoditylist` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(100) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `commoditylist` */

insert  into `commoditylist`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`purchased_date`,`remark`) values (1,'xads啊啊','萨达',5,1,1,'阿萨德','2019-04-29',''),(2,'xads啊啊','萨达',7,1,1,'阿萨德','2019-04-29',''),(3,'xads啊啊','萨达',1,1,1,'阿萨德','2019-04-29',''),(4,'xx','adasd',7,1,NULL,'xz','2019-04-30',''),(5,'xads啊啊','萨达',7,1,NULL,'阿萨德','2019-04-29',''),(6,'xads啊啊','萨达',3,1,NULL,'阿萨德','2019-04-29',''),(7,'xx','adasd',3,1,NULL,'xz','2019-04-30',''),(8,'xads啊啊','萨达',3,1,NULL,'阿萨德','2019-04-29',''),(9,'xx','adasd',8,1,NULL,'xz','2019-04-30',''),(10,'xads啊啊','萨达',1,1,NULL,'阿萨德','2019-04-29',''),(11,'xx','adasd',1,1,NULL,'xz','2019-04-30',''),(12,'xads啊啊','萨达',1,1,NULL,'阿萨德','2019-04-29',''),(13,'xx','adasd',1,1,NULL,'xz','2019-04-30',''),(14,'xads啊啊','萨达',6,1,NULL,'阿萨德','2019-04-29',''),(15,'xx','adasd',6,1,NULL,'xz','2019-04-30','');

/*Table structure for table `maintain` */

DROP TABLE IF EXISTS `maintain`;

CREATE TABLE `maintain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `license` varchar(10) DEFAULT NULL,
  `mileage` varchar(10) DEFAULT NULL,
  `CompulsoryInsurance` date DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `userID` int(10) unsigned DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `a` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
  `purchasedDate` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `commodityId` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `outbound` */

insert  into `outbound`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`addr`,`purchasedDate`,`making`,`Handle`,`remark`,`commodityId`) values (2,'xx','城南宏能汽车',7,1,7,'湖南鑫亿德汽车服务有限公司','长沙市城汽配城2栋132-133 0731-88577735','2019-04-30','管理员','余琴','','5'),(3,'xxx','城南宏能汽车',11,1,11,'湖南鑫亿德汽车服务有限公司','长沙市城汽配城2栋132-133 0731-88577735','2019-04-30','管理员','余琴','','8,9');

/*Table structure for table `repair` */

DROP TABLE IF EXISTS `repair`;

CREATE TABLE `repair` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `license` varchar(10) DEFAULT NULL,
  `mileage` varchar(10) DEFAULT NULL,
  `CompulsoryInsurance` date DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `userID` int(10) unsigned DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `a` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `repair` */

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

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
  `level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=gbk;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
