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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `generos` (`id`, `genero`) VALUES
	(1, 'CIENCIA FICCIÓN'),
	(2, 'TERROR'),
	(3, 'AVENTURA'),
	(4, 'COMEDIA'),
	(5, 'ROMANCE'),
	(6, 'DRAMA'),
	(7, 'DISTOPÍA'),
	(8, 'REALISMO MÁGICO'),
	(9, 'FANTASÍA ÉPICA'),
	(10, 'THRILLER');

CREATE TABLE IF NOT EXISTS `libros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `autor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `editorial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_genero` int NOT NULL,
  `imagen` varchar(255) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_genero` (`id_genero`),
  CONSTRAINT `id_genero` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `libros` (`id`, `titulo`, `autor`, `editorial`, `id_genero`, `imagen`, `stock`) VALUES
	(10, 'CIEN AÑOS DE SOLEDAD', 'GABRIEL GARCÍA MARQUÉZ', 'EDITORIAL SUDAMERICANA', 8, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\59f5878d-482a-49ac-8d99-cffc38037281.jfif', 0),
	(11, '1984', 'GEORGE ORWELL', 'SECKER AND WARBURG', 7, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\George Orwell.jfif', 0),
	(12, 'EL SEÑOR DE LOS ANILLOS', 'J.R.R TOLKIEN', 'GEORGE ALLEN', 9, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\el señor de los anillos.jfif', 0),
	(13, 'ORGULLO Y PREJUICIO', 'JANE AUSTEN', 'T. EGERTON', 5, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\orgullo y prejuicio.jfif', 0),
	(14, 'DON QUIJOTE DE LA MANCHA', 'MIGUEL DE CERVANTES SAAVEDRA', 'FRANCISCO DE ROBLES', 3, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\don quijote.jfif', 0),
	(15, 'HARRY POTTER Y LA PIEDRA FILOSOFAL', 'J.K. ROWLING', 'BLOOMSBURY', 3, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\harry potter y la piedra.jfif', 0),
	(16, 'LA SOMBRA DEL VIENTO', 'CARLOS RUIZ ZAFÓN', 'PLANETA', 10, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\la sombra del viento.jfif', 0),
	(17, 'MATAR UN RUISEÑOR', 'HARPER LEE', 'J.B LIPPINCOTT', 6, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\matar a un ruiseñor.jfif', 0),
	(18, 'CRIMEN Y CASTIGO', 'FYODOR DOSTOEVSKY', 'THE RUSSIAN MESSENGER', 2, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\crimen y castigo.jfif', 0),
	(19, 'LA CASA DE LOS ESPÍRITUS', 'ISABEL ALLENDE', 'PLAZA & JANÉS', 8, 'C:\\Users\\HP\\Pictures\\Biblioteca Java\\la casa de los espiritus.jfif', 11),
	(20, 'Popeye', 'don quijote', 'san jose', 7, 'C:\\Users\\victu\\OneDrive\\Imágenes\\shuerk.jpg', 21),
	(21, 'Popeye', 'don quijote', 'san jose', 7, 'C:\\Users\\victu\\OneDrive\\Imágenes\\shuerk.jpg', 30);

CREATE TABLE IF NOT EXISTS `personas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `primer_nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `segundo_nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `primer_apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `segundo_apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `documento` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `Documento` (`documento`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `personas` (`id`, `primer_nombre`, `segundo_nombre`, `primer_apellido`, `segundo_apellido`, `documento`) VALUES
	(18, 'NICOLÁS', 'ANTONIO', 'ARRIETA', 'LAGOS', '1099735735'),
	(19, 'JOSE', 'DAVID', 'HERNANDEZ', 'NAVAJA', '1073813055');

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
	(1, 18, 15, 1),
	(2, 18, 20, 2),
	(3, 19, 10, 1),
	(4, 19, 16, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
