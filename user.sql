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
  `purchasedDate` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `commodityId` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `billentry` */

insert  into `billentry`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`addr`,`purchasedDate`,`making`,`Handle`,`remark`,`commodityId`) values (1,'asdaaaaa','asd',0,1,0,'asd','ad','2019-04-27','asd','asd','ads',NULL);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `commodity` */

insert  into `commodity`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`purchased_date`,`remark`) values (1,'xas','阿萨德',212,213,23123,'啊实打实的','2019-04-27','奥术大师大所大声道'),(2,'xdsd','czdsa',312,342,435345,'啊实打实的','2019-04-27','春风骀荡多多多多多多');

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
  `purchasedDate` date DEFAULT NULL,
  `making` varchar(45) DEFAULT NULL COMMENT '制单人',
  `Handle` varchar(45) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(300) DEFAULT NULL,
  `commodityId` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `outbound` */

insert  into `outbound`(`id`,`no`,`name`,`number`,`price`,`money`,`manufacturer`,`addr`,`purchasedDate`,`making`,`Handle`,`remark`,`commodityId`) values (1,'x0011111','先锋',0,1,0,'史蒂夫大幅度','153131564长沙','2019-04-27','小升初','初学者','',NULL),(2,'asd','先锋2',524,1,458468,'xz','asd','2019-04-27','asd','asd','asdasda','1,2'),(3,'ads','asdads',524,1,458468,'asd','asd','2019-04-27','','asd','sad','1,2');

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
