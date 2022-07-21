CREATE DATABASE `ticket_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `actors` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          `country_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FK2yo5yhg5xhkxjsdqcmlk8qali` (`country_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cinema_halls` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `capacity` int NOT NULL,
                                `description` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `countries` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movie_sessions` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `showTime` datetime(6) DEFAULT NULL,
                                  `cinemaHall_id` bigint DEFAULT NULL,
                                  `movie_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKk04umq7ulfxwqtgoqf59a0reu` (`cinemaHall_id`),
                                  KEY `FKatpmn0h31nwhwdgd0ogr8q6kj` (`movie_id`),
                                  CONSTRAINT `FKatpmn0h31nwhwdgd0ogr8q6kj` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
                                  CONSTRAINT `FKk04umq7ulfxwqtgoqf59a0reu` FOREIGN KEY (`cinemaHall_id`) REFERENCES `cinema_halls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movies` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `title` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `movies_actors` (
                                 `Movie_id` bigint NOT NULL,
                                 `actors_id` bigint NOT NULL,
                                 KEY `FKi9nbemods4rqk1geesbsgjpl1` (`actors_id`),
                                 KEY `FKby1tsplsi2bx1x159doaxfak7` (`Movie_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
