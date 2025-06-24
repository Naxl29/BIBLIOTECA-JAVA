/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `biblioteca_java` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `biblioteca_java`;

CREATE TABLE IF NOT EXISTS `estados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `estado` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `estados` (`id`, `estado`) VALUES
	(1, 'PRESTADO'),
	(2, 'DEVUELTO');

CREATE TABLE IF NOT EXISTS `generos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `genero` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `generos` (`id`, `genero`) VALUES
	(1, 'CIENCIA FICCIÓN'),
	(2, 'TERROR'),
	(3, 'AVENTURA'),
	(4, 'COMEDIA'),
	(5, 'ROMANCE'),
	(6, 'DRAMA'),
	(7, 'DISTOPÍA');

CREATE TABLE IF NOT EXISTS `libros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `autor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `editorial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_genero` int NOT NULL,
  `imagen` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_genero` (`id_genero`),
  CONSTRAINT `id_genero` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `libros` (`id`, `titulo`, `autor`, `editorial`, `id_genero`, `imagen`) VALUES
	(2, 'NUM', 'NUM', 'NUM', 1, ''),
	(3, 'NYM', 'NYM', 'NYM', 1, ''),
	(5, 'ldpdpd', 'sppsps', 'psps', 1, ''),
	(6, 'kdddsss', 'aaaaa', 'ddddd', 4, '');

CREATE TABLE IF NOT EXISTS `personas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `primer_nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `segundo_nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `primer_apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `segundo_apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `documento` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `Documento` (`documento`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `personas` (`id`, `primer_nombre`, `segundo_nombre`, `primer_apellido`, `segundo_apellido`, `documento`) VALUES
	(12, 'NICOLAS', 'ANTONIO', 'ARRIETA', 'LAGOS', '1099735735'),
	(13, 'HAROL', 'SANTIAGO', 'RODRIGUEZ', 'CASALLAS', '111111'),
	(14, 'ns', 'ns', 'ns', 'ns', '333333');

CREATE TABLE IF NOT EXISTS `prestamos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_persona` int NOT NULL,
  `id_libro` int NOT NULL,
  `id_estado` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_persona` (`id_persona`),
  KEY `id_libro` (`id_libro`),
  KEY `id_estado` (`id_estado`),
  CONSTRAINT `id_estado` FOREIGN KEY (`id_estado`) REFERENCES `estados` (`id`),
  CONSTRAINT `id_libro` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id`),
  CONSTRAINT `id_persona` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `prestamos` (`id`, `id_persona`, `id_libro`, `id_estado`) VALUES
	(2, 12, 2, 1),
	(3, 13, 3, 1),
	(4, 14, 2, 2),
	(5, 12, 6, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
