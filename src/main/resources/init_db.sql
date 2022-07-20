CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8;
USE `cinema`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies`  (
                           `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                           `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                           `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cinemaHalls
-- ----------------------------
DROP TABLE IF EXISTS `cinemaHalls`;
CREATE TABLE `cinemaHalls`  (
                                `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                                `capacity` int NOT NULL,
                                `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movieSessions
-- ----------------------------
DROP TABLE IF EXISTS `movieSessions`;
CREATE TABLE `movieSessions`  (
                                  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                                  `movie_id` bigint UNSIGNED NOT NULL,
                                  `cinemaHall_id` bigint UNSIGNED NOT NULL,
                                  `showTime` DATETIME NOT NULL,
                                  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `FK_movie_id`(`movie_id`) USING BTREE,
                                  CONSTRAINT `FK_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  INDEX `FK_cinemaHall_id`(`cinemaHall_id`) USING BTREE,
                                  CONSTRAINT `FK_cinemaHall_id` FOREIGN KEY (`cinemaHall_id`) REFERENCES `cinemaHalls` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;