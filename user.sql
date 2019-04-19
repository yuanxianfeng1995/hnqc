/*
SQLyog Ultimate v12.5.0 (64 bit)
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=gbk;

/*Data for the table `banji` */

/*Table structure for table `billentry` */

DROP TABLE IF EXISTS `billentry`;

CREATE TABLE `billentry` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `billentry` */

/*Table structure for table `outbound` */

DROP TABLE IF EXISTS `outbound`;

CREATE TABLE `outbound` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `purchased_date` date DEFAULT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `outbound` */

insert  into `outbound`(`id`,`name`,`manufacturer`,`price`,`purchased_date`,`remark`) values 
(1,'asd','zxczczxzc',150,'2019-04-16','asdsaaaaaaaaaaaaaaaaaaa'),
(2,'哇','asddas',150,NULL,'asddddddddddd'),
(3,'阿斯达','阿斯达',0,'2019-04-17','阿斯达'),
(4,'长沙','行政村',0,'2019-04-17','自行车'),
(5,'asd','asd',0,'2019-04-18','asd');

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=gbk;

/*Data for the table `student` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `xuehao` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `sex` varchar(45) NOT NULL,
  `age` varchar(45) NOT NULL,
  `weight` varchar(45) NOT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

/*Data for the table `user` */

insert  into `user`(`id`,`xuehao`,`name`,`sex`,`age`,`weight`,`birthday`) values 
(1,'2014020001','速度','男','20','120',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
