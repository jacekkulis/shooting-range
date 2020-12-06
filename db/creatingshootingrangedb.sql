CREATE DATABASE IF NOT EXISTS `shootingrangedb`;
USE `shootingrangedb`;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;


LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'ROLE_USER');
INSERT INTO `role` VALUES (2,'ROLE_ADMIN');
UNLOCK TABLES;



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_role_roleid_idx` (`role_id`),
  CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;


DROP TABLE IF EXISTS `referee`;
CREATE TABLE `referee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

LOCK TABLES `referee` WRITE;
INSERT INTO `referee` VALUES (1, 'Patrick', 'Moore', 'World class');
INSERT INTO `referee` VALUES (2, 'Michael', 'Whapples', 'First class');
INSERT INTO `referee` VALUES (3, 'Dane', 'Robertson', 'Second class');
INSERT INTO `referee` VALUES (4, 'John', 'Snow', 'Third class');
UNLOCK TABLES;


DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `typeOfGun` varchar(255) DEFAULT NULL,
  `numberOfCompetitors` int(11) DEFAULT NULL,
  `img` LONGBLOB DEFAULT NULL,
  `referee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_referee_id` (`referee_id`),
  CONSTRAINT `fk_referee_id` FOREIGN KEY (`referee_id`) REFERENCES `referee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

LOCK TABLES `event` WRITE;
INSERT INTO `event` VALUES (1, 'Sample', 'description1', 'Rifle', 5, null, 2);
INSERT INTO `event` VALUES (2, 'Sample2', 'description2description2description2description2description2description2description2description2', 'Pistol',  8, null, 2);
UNLOCK TABLES;