-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: proyectocriptografia
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `operaciones`
--

DROP TABLE IF EXISTS `operaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operaciones` (
  `idop` int(11) NOT NULL AUTO_INCREMENT,
  `operacion` varchar(50) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `filename` varchar(120) DEFAULT NULL,
  `idusuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idop`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `operaciones_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operaciones`
--

LOCK TABLES `operaciones` WRITE;
/*!40000 ALTER TABLE `operaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `operaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `nombre` varchar(100) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `pass` varchar(200) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permisos` varchar(20) DEFAULT NULL,
  `keyMac` varchar(344) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('Rafael Hernandez Ruiz','empleado','rafassassinscreed@gmail.com','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5',1,'ALL','oATkHdpoO3j6EksoqzlUEjoAEtnWTVCpKAzqDUjYG62HSvvVlP56nNxMhykiB+Tdp/ZwCz3vEjS3yLt2dnd0NUE/QhHK0lG3UH/bLYPtGSv6gtaH7MvnnON6/FgkoF9JJ5vUpLfNJdTM7o5GMA/Ebj1w7zZQY2nYgMcEwEeUaSKMpu9HYwdBP1eXAKEzifFXBvq1DsPS1Roirw1BeVYXWJ5Ud52vXemjLd/z+j928SkfNPEC5pULd8QW/MQEN71PLtyvfDV0rmnLGJjLqJ/ie1C+2KY6GTvVQQp8kHOq6eHnrrCK4mAq4KZG1718BQ5HDAMimCoiv99JJDCwPsaHow=='),('Humberto Dominguez','empleado','beto@gmail.com','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5',2,'ALL','hPHZ9F60Jwh4WXTpk9ygvc3MnWQHQH8uA5SuS/3cotr4VJ3nR2e5KS8xk1GfOFS4kluGjErwgspT/ln64unepU9WwT3Y4phoGVroZF2IWhyT5crprvYp+VCiW7k6wLgxRPeMDXRdIxpwE44CzBVoiKuNkIKcVqUZY1/7WpYB+NlFX1StsE6KsenSWwLA9AMbEjTW/u4yyVJVx7udRTvjP9zBGWei1eqcuxpf2c8NEL7Yx0Tjra1UlaC8stDGicfewI17s7XHwI4y2Nf9Bho6fJSah92jsLLTgrUbvW0KIXOSqYZyF5ktuLQGz1sGGlUelc54v7WJG8kvyPGlfGAoYg=='),('Humberto','Administrador','beto@gmail.com','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5',3,'ALL',NULL),('Humberto','Administrador','beto2@gmail.com','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5',4,'ALL','n4cjxa9vQXUc2bNi2o5+olKC958XTOnW/3o1a1DjvC//tJnsfq6k2g+0LDounGG327LhopjSzVEvAB6vGlxDp1P83eX7Vv8Up4lltbzTpW94Fo/VTZAVed41GKQhXHRzZCvddQ/lU2mi4L/HUBMoEzwvd9QhkJatjzNHKnV8C83vI8bF2FD9WExjGnxtu6kWx87YIrs7qoW+zOd5A7Tdc2Kw0m6yJfdKOGVMtv3VXYJr57IC2UQixkWFPBzsJ5MTnh4ROa3+QDjpNQQPGtPl8iP2fKR993aSqOgm4eMOF/qAAUsUGP2phnAd4R/QXAJ+LQw7aSVu5aA6YXbuVZQIaw==');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-11 13:15:27
