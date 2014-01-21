/*
 Run this SQL file on your DB to create the table/schema needed for the MySQL logger.
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `gradingrecords`
-- ----------------------------
DROP TABLE IF EXISTS `gradingrecords`;
CREATE TABLE `gradingrecords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(45) DEFAULT NULL,
  `projectId` varchar(255) DEFAULT NULL,
  `onyen` varchar(45) DEFAULT NULL,
  `timestamp` timestamp(6) NULL DEFAULT NULL,
  `gradable` varchar(255) DEFAULT NULL,
  `score` double(128,0) DEFAULT NULL,
  `notes` text,
  `results` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
