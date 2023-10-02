-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: favorites
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqa4k6p9mhd8aa4xc39uygxwut` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (7,'Agility'),(6,'DEV'),(1,'lacategorie'),(13,'Le fromage');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_at` date DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `link` text,
  `validity` bit(1) NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8xxn3avi43q7xqi2jaehj8gde` (`category_id`),
  CONSTRAINT `FK8xxn3avi43q7xqi2jaehj8gde` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (1,'2023-09-29','recherche google','https://www.mysqltutorial.org/mysql-now/#:~:text=You%20can%20use%20the%20NOW,default%20value%20is%20NOW()%20.',_binary '',1),
(2,'2023-09-29','recherche google','https://www.google.com/search?q=angularjs+foreach+is+not+a+function&sca_esv=569281890&sxsrf=AM9HkKmvx-m27JD2p52Y6j2mKAV9iJxLHg%3A1695940625725&ei=EQAWZYLrK5bikdUPhqms6AQ&ved=0ahUKEwjCt7mUr86BAxUWcaQEHYYUC00Q4dUDCBA&uact=5',_binary '\0',1),(3,'2023-09-29','recherche google','https://www.google.com/search?q=angularjs+foreach+is+not+a+function&sca_esv=569281890&sxsrf=AM9HkKmvx-m27JD2p52Y6j2mKAV9iJxLHg%3A1695940625725&ei=EQAWZYLrK5bikdUPhqms6AQ&ved=0ahUKEwjCt7mUr86BAxUWcaQEHYYUC00Q4dUDCBA&uact=5&oq=angularjs+foreach+is+not+a+function&gs_lp',_binary '\0',1),(4,'2023-09-29','recherche google','https://www.google.com/search?q=lyrics+songs+girl+i+know+what+you+want&sca_esv=569245889&sxsrf=AM9HkKmvxsOvq8XzaDj-ivwHm5fWqJv2fg%3A1695930706389&ei=UtkVZaKrF8SpkdUP76-y4Ag&oq=lyrics+songs+girl+I+know+what&gs_lp=Egxnd3Mtd2l6LXNlcnAiHWx5cmljcyBzb25ncyBnaXJsIEkga25vdyB3aGF0KgIIADIFECEYoAEyBRAhGKABMgUQIRigATIIECEYFhgeGB0yCBAhGBYYHhgdSPNmUN4CWIRYcAl4AZABAJgBb6ABqw6qAQQxOC4zuAEDyAEA-AEBwgIKEAAYRxjWBBiwA8ICChAAGIoFGLADGEPCAgUQABiABMICCBAAGMsBGIAEwgIGEAAYFhgewgIIEAAYFhgeGArCAgsQIRgWGB4Y8QQYHcICBxAhGKABGArCAgoQIRgWGB4YDxgdwgIEECEYFeIDBBgAIEGIBgGQBgo&sclient=gws-wiz-serp',_binary '\0',1),(5,'2023-09-29','tezsdfs','https://www.google.com/search?q=lyrics+songs+girl+i+know+what+you+want&sca_esv=569245889&sxsrf=AM9HkKmvxsOvq8XzaDj-ivwHm5fWqJv2fg%3A1695930706389&ei=UtkVZaKrF8SpkdUP76-y4Ag&oq=lyrics+songs+girl+I+know+what&gs_lp=Egxnd3Mtd2l6LXNlcnAiHWx5cmljcyBzb25ncyBnaXJsIEkga25vdyB3aGF0KgIIADIFECEYoAEyBRAhGKABMgUQIRigATIIECEYFhgeGB0yCBAhGBYYHhgdSPNmUN4CWIRYcAl4AZABAJgBb6ABqw6qAQQxOC4zuAEDyAEA-AEBwgIKEAAYRxjWBBiwA8ICChAAGIoFGLADGEPCAgUQABiABMICCBAAGMsBGIAEwgIGEAAYFhgewgIIEAAYFhgeGArCAgsQIRgWGB4Y8QQYHcICBxAhGKABGArCAgoQIRgWGB4YDxgdwgIEECEYFeIDBBgAIEGIBgGQBgo&sclient=gws-wiz-serp',_binary '\0',7),(6,'2023-09-29','recherche google','https://www.mysqltutorial.org/mysql-now/#:~:text=You%20can%20use%20the%20NOW,default%20value%20is%20NOW()%20..',_binary '',1),(7,'2023-09-29','recherche google','https://www.mysqltutorial.org/mysql-now/#:~:text=You%20can%20use%20the%20NOW,default%20value%20is%20NOW()%20...',_binary '',1),(8,'2023-09-29','recherche google','https://www.mysq.ltut.orial.org/mysql-now/#:~:text=You%20can%20use%20the%20NOW,default%20value%20is%20NOW()%20...',_binary '\0',1),(9,'2023-09-29','recherche google','https://www.mysq.ltut.orial.org/mysql-now/#:~:t.e.x.t=You%20can%20use%20the%20NOW,default%20value%20is%20NOW()%20...',_binary '\0',1);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29 14:01:59
