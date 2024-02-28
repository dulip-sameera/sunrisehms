CREATE DATABASE  IF NOT EXISTS `sunrisehms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sunrisehms`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: sunrisehms
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nic` varchar(12) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `customer_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9st6x9trhf0s27g0vgpcaeu3m` (`nic`),
  UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  KEY `FK2r41502dbwehta0hpw1h1iml0` (`customer_status_id`),
  CONSTRAINT `FK2r41502dbwehta0hpw1h1iml0` FOREIGN KEY (`customer_status_id`) REFERENCES `customer_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Aarav','Silva','Nimal','aarav.silva@email.com','874512309V','0775468201',1),(2,'Kavya','Rajapakse','Devi','kavya.rajapakse@email.com','652134780V','0712345678',1),(3,'Vikram','Perera','Nadeesh','vikram.perera@email.com','583210497V','0776543210',1),(4,'Anika','Fernando','Tharushi','anika.fernando@email.com','456789012V','0721098765',1),(5,'Niran','Bandara','Sampath','niran.bandara@email.com','321098765V','0754567890',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_status`
--

DROP TABLE IF EXISTS `customer_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_status`
--

LOCK TABLES `customer_status` WRITE;
/*!40000 ALTER TABLE `customer_status` DISABLE KEYS */;
INSERT INTO `customer_status` VALUES (1,'ACTIVE'),(2,'DELETED');
/*!40000 ALTER TABLE `customer_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) NOT NULL,
  `task_id` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4e8v3emcgfqulikhgmp5xfj9q` (`task_id`),
  KEY `FK3wxdofviqe2smmvh1w1yf98o1` (`user_id`),
  CONSTRAINT `FK3wxdofviqe2smmvh1w1yf98o1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK4e8v3emcgfqulikhgmp5xfj9q` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,'2024-02-28 19:07:59.256000',1,1),(2,'2024-02-28 19:09:49.843000',3,1),(3,'2024-02-28 19:10:47.362000',7,1),(4,'2024-02-28 19:11:09.345000',7,1),(5,'2024-02-28 19:11:43.754000',7,1),(6,'2024-02-28 19:11:50.665000',9,1),(7,'2024-02-28 19:12:07.705000',7,1),(8,'2024-02-28 19:12:24.009000',7,1),(9,'2024-02-28 19:12:42.040000',7,1),(10,'2024-02-28 19:13:37.627000',11,1),(11,'2024-02-28 19:13:37.644000',8,1),(12,'2024-02-28 19:13:58.394000',11,1),(13,'2024-02-28 19:13:58.409000',8,1),(14,'2024-02-28 19:13:58.416000',8,1),(15,'2024-02-28 19:14:20.621000',11,1),(16,'2024-02-28 19:14:20.639000',8,1),(17,'2024-02-28 19:14:20.647000',8,1),(18,'2024-02-28 19:14:20.656000',8,1),(19,'2024-02-28 19:14:46.177000',8,1),(20,'2024-02-28 19:14:46.185000',8,1),(21,'2024-02-28 19:14:46.193000',8,1),(22,'2024-02-28 19:15:00.915000',11,1),(23,'2024-02-28 19:15:00.931000',8,1),(24,'2024-02-28 19:15:00.940000',8,1),(25,'2024-02-28 19:15:00.963000',8,1),(26,'2024-02-28 19:15:00.976000',8,1),(27,'2024-02-28 19:15:10.966000',8,1),(28,'2024-02-28 19:15:10.975000',8,1),(29,'2024-02-28 19:15:10.981000',8,1),(30,'2024-02-28 19:15:10.990000',8,1),(31,'2024-02-28 19:15:29.253000',8,1),(32,'2024-02-28 19:15:29.261000',8,1),(33,'2024-02-28 19:15:29.270000',8,1),(34,'2024-02-28 19:15:29.277000',8,1),(35,'2024-02-28 19:17:41.318000',8,1),(36,'2024-02-28 19:17:41.327000',8,1),(37,'2024-02-28 19:17:41.333000',8,1),(38,'2024-02-28 19:17:41.340000',8,1),(39,'2024-02-28 19:18:01.276000',8,1),(40,'2024-02-28 19:18:01.286000',8,1),(41,'2024-02-28 19:18:01.292000',8,1),(42,'2024-02-28 19:18:01.300000',8,1),(43,'2024-02-28 19:18:21.393000',11,1),(44,'2024-02-28 19:18:21.409000',8,1),(45,'2024-02-28 19:18:21.416000',8,1),(46,'2024-02-28 19:18:21.421000',8,1),(47,'2024-02-28 19:18:21.426000',8,1),(48,'2024-02-28 19:18:21.432000',8,1),(49,'2024-02-28 19:19:36.375000',8,1),(50,'2024-02-28 19:19:36.385000',8,1),(51,'2024-02-28 19:19:36.390000',8,1),(52,'2024-02-28 19:19:36.394000',8,1),(53,'2024-02-28 19:19:36.398000',8,1),(54,'2024-02-28 19:20:05.561000',11,1),(55,'2024-02-28 19:20:05.575000',8,1),(56,'2024-02-28 19:20:05.583000',8,1),(57,'2024-02-28 19:20:05.590000',8,1),(58,'2024-02-28 19:20:05.594000',8,1),(59,'2024-02-28 19:20:05.599000',8,1),(60,'2024-02-28 19:20:05.605000',8,1),(61,'2024-02-28 19:21:27.248000',8,1),(62,'2024-02-28 19:21:27.254000',8,1),(63,'2024-02-28 19:21:27.260000',8,1),(64,'2024-02-28 19:21:27.269000',8,1),(65,'2024-02-28 19:21:27.277000',8,1),(66,'2024-02-28 19:21:27.284000',8,1),(67,'2024-02-28 19:21:37.077000',8,1),(68,'2024-02-28 19:21:37.083000',8,1),(69,'2024-02-28 19:21:37.087000',8,1),(70,'2024-02-28 19:21:37.092000',8,1),(71,'2024-02-28 19:21:37.098000',8,1),(72,'2024-02-28 19:21:37.106000',8,1),(73,'2024-02-28 19:21:51.544000',11,1),(74,'2024-02-28 19:21:51.556000',8,1),(75,'2024-02-28 19:21:51.561000',8,1),(76,'2024-02-28 19:21:51.572000',8,1),(77,'2024-02-28 19:21:51.577000',8,1),(78,'2024-02-28 19:21:51.582000',8,1),(79,'2024-02-28 19:21:51.587000',8,1),(80,'2024-02-28 19:21:51.592000',8,1),(81,'2024-02-28 19:22:08.131000',11,1),(82,'2024-02-28 19:22:08.148000',8,1),(83,'2024-02-28 19:22:08.155000',8,1),(84,'2024-02-28 19:22:08.159000',8,1),(85,'2024-02-28 19:22:08.166000',8,1),(86,'2024-02-28 19:22:08.173000',8,1),(87,'2024-02-28 19:22:08.180000',8,1),(88,'2024-02-28 19:22:08.187000',8,1),(89,'2024-02-28 19:22:08.193000',8,1),(90,'2024-02-28 19:22:15.853000',8,1),(91,'2024-02-28 19:22:15.859000',8,1),(92,'2024-02-28 19:22:15.867000',8,1),(93,'2024-02-28 19:22:15.873000',8,1),(94,'2024-02-28 19:22:15.878000',8,1),(95,'2024-02-28 19:22:15.882000',8,1),(96,'2024-02-28 19:22:15.886000',8,1),(97,'2024-02-28 19:22:15.890000',8,1),(98,'2024-02-28 19:22:38.223000',8,1),(99,'2024-02-28 19:22:38.230000',8,1),(100,'2024-02-28 19:22:38.235000',8,1),(101,'2024-02-28 19:22:38.241000',8,1),(102,'2024-02-28 19:22:38.247000',8,1),(103,'2024-02-28 19:22:38.253000',8,1),(104,'2024-02-28 19:22:38.258000',8,1),(105,'2024-02-28 19:22:38.261000',8,1),(106,'2024-02-28 19:22:50.356000',8,1),(107,'2024-02-28 19:22:50.361000',8,1),(108,'2024-02-28 19:22:50.367000',8,1),(109,'2024-02-28 19:22:50.373000',8,1),(110,'2024-02-28 19:22:50.383000',8,1),(111,'2024-02-28 19:22:50.388000',8,1),(112,'2024-02-28 19:22:50.392000',8,1),(113,'2024-02-28 19:22:50.397000',8,1),(114,'2024-02-28 19:23:02.929000',11,1),(115,'2024-02-28 19:23:02.945000',8,1),(116,'2024-02-28 19:23:02.949000',8,1),(117,'2024-02-28 19:23:02.953000',8,1),(118,'2024-02-28 19:23:02.958000',8,1),(119,'2024-02-28 19:23:02.963000',8,1),(120,'2024-02-28 19:23:02.970000',8,1),(121,'2024-02-28 19:23:02.976000',8,1),(122,'2024-02-28 19:23:02.980000',8,1),(123,'2024-02-28 19:23:02.985000',8,1),(124,'2024-02-28 19:23:15.538000',11,1),(125,'2024-02-28 19:23:15.552000',8,1),(126,'2024-02-28 19:23:15.558000',8,1),(127,'2024-02-28 19:23:15.566000',8,1),(128,'2024-02-28 19:23:15.575000',8,1),(129,'2024-02-28 19:23:15.581000',8,1),(130,'2024-02-28 19:23:15.585000',8,1),(131,'2024-02-28 19:23:15.589000',8,1),(132,'2024-02-28 19:23:15.593000',8,1),(133,'2024-02-28 19:23:15.599000',8,1),(134,'2024-02-28 19:23:15.604000',8,1),(135,'2024-02-28 19:23:26.751000',8,1),(136,'2024-02-28 19:23:26.756000',8,1),(137,'2024-02-28 19:23:26.760000',8,1),(138,'2024-02-28 19:23:26.765000',8,1),(139,'2024-02-28 19:23:26.774000',8,1),(140,'2024-02-28 19:23:26.779000',8,1),(141,'2024-02-28 19:23:26.783000',8,1),(142,'2024-02-28 19:23:26.788000',8,1),(143,'2024-02-28 19:23:26.793000',8,1),(144,'2024-02-28 19:23:26.798000',8,1),(145,'2024-02-28 19:28:09.204000',15,1),(146,'2024-02-28 19:28:54.313000',15,1),(147,'2024-02-28 19:29:40.864000',15,1),(148,'2024-02-28 19:30:17.469000',15,1),(149,'2024-02-28 19:31:11.430000',15,1),(150,'2024-02-28 19:31:24.437000',8,1),(151,'2024-02-28 19:31:24.441000',8,1),(152,'2024-02-28 19:31:24.446000',8,1),(153,'2024-02-28 19:31:24.451000',8,1),(154,'2024-02-28 19:31:24.456000',8,1),(155,'2024-02-28 19:31:24.463000',8,1),(156,'2024-02-28 19:31:24.468000',8,1),(157,'2024-02-28 19:31:24.473000',8,1),(158,'2024-02-28 19:31:24.479000',8,1),(159,'2024-02-28 19:31:24.487000',8,1),(160,'2024-02-28 19:31:41.285000',16,1),(161,'2024-02-28 19:31:55.399000',8,1),(162,'2024-02-28 19:31:55.412000',8,1),(163,'2024-02-28 19:31:55.417000',8,1),(164,'2024-02-28 19:31:55.423000',8,1),(165,'2024-02-28 19:32:41.215000',12,1),(166,'2024-02-28 19:32:41.224000',12,1),(167,'2024-02-28 19:32:41.249000',19,1),(168,'2024-02-28 19:32:43.714000',16,1),(170,'2024-02-28 19:32:43.756000',12,1),(171,'2024-02-28 19:32:43.762000',12,1),(172,'2024-02-28 19:32:43.774000',8,1),(173,'2024-02-28 19:32:43.777000',8,1),(174,'2024-02-28 19:32:43.782000',8,1),(175,'2024-02-28 19:32:43.790000',8,1),(176,'2024-02-28 19:32:43.795000',8,1),(177,'2024-02-28 19:32:43.800000',8,1),(178,'2024-02-28 19:32:43.807000',8,1),(179,'2024-02-28 19:32:43.810000',8,1),(180,'2024-02-28 19:32:52.229000',8,1),(181,'2024-02-28 19:32:52.234000',8,1),(182,'2024-02-28 19:32:52.241000',8,1),(183,'2024-02-28 19:32:52.245000',8,1),(184,'2024-02-28 19:32:52.249000',8,1),(185,'2024-02-28 19:32:52.255000',8,1),(186,'2024-02-28 19:32:52.262000',8,1),(187,'2024-02-28 19:32:52.266000',8,1),(188,'2024-02-28 19:32:52.273000',8,1),(189,'2024-02-28 19:32:52.277000',8,1),(190,'2024-02-28 19:33:01.060000',8,1),(191,'2024-02-28 19:33:01.065000',8,1),(192,'2024-02-28 19:33:01.072000',8,1),(193,'2024-02-28 19:33:01.076000',8,1),(194,'2024-02-28 19:33:01.081000',8,1),(195,'2024-02-28 19:33:01.087000',8,1),(196,'2024-02-28 19:33:01.091000',8,1),(197,'2024-02-28 19:33:01.096000',8,1),(198,'2024-02-28 19:33:14.623000',16,1),(199,'2024-02-28 19:33:35.509000',8,1),(200,'2024-02-28 19:33:35.520000',8,1),(201,'2024-02-28 19:33:50.461000',12,1),(202,'2024-02-28 19:33:50.470000',19,1),(203,'2024-02-28 19:33:59.771000',8,1),(204,'2024-02-28 19:33:59.777000',8,1),(205,'2024-02-28 19:33:59.781000',8,1),(206,'2024-02-28 19:33:59.786000',8,1),(207,'2024-02-28 19:33:59.792000',8,1),(208,'2024-02-28 19:33:59.796000',8,1),(209,'2024-02-28 19:33:59.800000',8,1),(210,'2024-02-28 19:34:19.460000',8,1),(211,'2024-02-28 19:34:19.468000',8,1),(212,'2024-02-28 19:34:19.472000',8,1),(213,'2024-02-28 19:34:19.477000',8,1),(214,'2024-02-28 19:34:19.483000',8,1),(215,'2024-02-28 19:34:19.488000',8,1),(216,'2024-02-28 19:34:19.493000',8,1),(217,'2024-02-28 19:34:43.350000',16,1),(218,'2024-02-28 19:34:47.588000',17,1),(219,'2024-02-28 19:35:38.260000',8,1),(220,'2024-02-28 19:35:38.267000',8,1),(221,'2024-02-28 19:35:38.272000',8,1),(222,'2024-02-28 19:35:38.276000',8,1),(223,'2024-02-28 19:35:38.280000',8,1),(224,'2024-02-28 19:35:38.283000',8,1),(225,'2024-02-28 19:35:38.286000',8,1),(226,'2024-02-28 19:35:45.820000',16,1),(227,'2024-02-28 19:35:57.124000',8,1),(228,'2024-02-28 19:35:57.134000',8,1),(229,'2024-02-28 19:36:14.989000',12,1),(230,'2024-02-28 19:36:22.140000',12,1),(231,'2024-02-28 19:36:22.149000',19,1),(232,'2024-02-28 19:36:33.308000',8,1),(233,'2024-02-28 19:36:33.312000',8,1),(234,'2024-02-28 19:36:33.316000',8,1),(235,'2024-02-28 19:36:33.320000',8,1),(236,'2024-02-28 19:36:33.326000',8,1),(237,'2024-02-28 19:36:33.334000',8,1),(238,'2024-02-28 19:36:33.342000',8,1),(239,'2024-02-28 19:36:33.346000',8,1),(240,'2024-02-28 19:36:33.349000',8,1),(241,'2024-02-28 19:36:33.352000',8,1),(242,'2024-02-28 19:36:40.800000',16,1),(244,'2024-02-28 19:36:40.827000',12,1),(245,'2024-02-28 19:36:40.838000',8,1),(246,'2024-02-28 19:36:40.846000',8,1),(247,'2024-02-28 19:36:40.855000',8,1),(248,'2024-02-28 19:36:40.859000',8,1),(249,'2024-02-28 19:36:40.864000',8,1),(250,'2024-02-28 19:36:40.869000',8,1),(251,'2024-02-28 19:36:53.703000',16,1),(253,'2024-02-28 19:36:53.729000',12,1),(254,'2024-02-28 19:36:53.739000',8,1),(255,'2024-02-28 19:36:53.743000',8,1),(256,'2024-02-28 19:36:53.750000',8,1),(257,'2024-02-28 19:36:53.754000',8,1),(258,'2024-02-28 19:36:53.757000',8,1),(259,'2024-02-28 19:36:53.761000',8,1),(260,'2024-02-28 19:36:59.654000',16,1),(262,'2024-02-28 19:36:59.686000',12,1),(263,'2024-02-28 19:36:59.690000',12,1),(264,'2024-02-28 19:36:59.701000',8,1),(265,'2024-02-28 19:36:59.705000',8,1),(266,'2024-02-28 19:36:59.712000',8,1),(267,'2024-02-28 19:36:59.719000',8,1),(268,'2024-02-28 19:36:59.723000',8,1),(269,'2024-02-28 19:36:59.729000',8,1),(270,'2024-02-28 19:37:21.289000',8,1),(271,'2024-02-28 19:37:21.295000',8,1),(272,'2024-02-28 19:37:21.303000',8,1),(273,'2024-02-28 19:37:21.309000',8,1),(274,'2024-02-28 19:37:21.314000',8,1),(275,'2024-02-28 19:37:21.317000',8,1),(276,'2024-02-28 19:38:08.301000',8,1),(277,'2024-02-28 19:38:08.306000',8,1),(278,'2024-02-28 19:38:08.311000',8,1),(279,'2024-02-28 19:38:08.315000',8,1),(280,'2024-02-28 19:38:08.321000',8,1),(281,'2024-02-28 19:38:08.324000',8,1),(282,'2024-02-28 19:38:17.490000',16,1),(283,'2024-02-28 19:38:57.966000',8,1),(284,'2024-02-28 19:38:57.973000',8,1),(285,'2024-02-28 19:38:59.412000',8,1),(286,'2024-02-28 19:38:59.419000',8,1),(287,'2024-02-28 19:38:59.425000',8,1),(288,'2024-02-28 19:38:59.431000',8,1),(289,'2024-02-28 19:39:19.565000',12,1),(290,'2024-02-28 19:39:19.574000',19,1),(291,'2024-02-28 19:39:35.037000',16,1),(293,'2024-02-28 19:39:35.068000',12,1),(294,'2024-02-28 19:39:35.074000',8,1),(295,'2024-02-28 19:39:35.081000',8,1),(296,'2024-02-28 19:39:35.086000',8,1),(297,'2024-02-28 19:39:35.090000',8,1),(298,'2024-02-28 19:39:35.094000',8,1),(299,'2024-02-28 19:39:41.228000',12,1),(300,'2024-02-28 19:39:41.241000',21,1),(301,'2024-02-28 19:39:43.757000',16,1),(303,'2024-02-28 19:39:43.782000',12,1),(304,'2024-02-28 19:39:43.790000',8,1),(305,'2024-02-28 19:39:43.794000',8,1),(306,'2024-02-28 19:39:43.797000',8,1),(307,'2024-02-28 19:39:43.801000',8,1),(308,'2024-02-28 19:39:43.806000',8,1),(309,'2024-02-28 19:39:43.812000',8,1),(310,'2024-02-28 19:40:59.500000',8,1),(311,'2024-02-28 19:40:59.506000',8,1),(312,'2024-02-28 19:40:59.509000',8,1),(313,'2024-02-28 19:40:59.514000',8,1),(314,'2024-02-28 19:40:59.520000',8,1),(315,'2024-02-28 19:40:59.526000',8,1),(316,'2024-02-28 19:41:03.147000',16,1),(317,'2024-02-28 19:41:18.930000',8,1),(318,'2024-02-28 19:41:21.234000',8,1),(319,'2024-02-28 19:41:21.240000',8,1),(320,'2024-02-28 19:41:40.876000',12,1),(321,'2024-02-28 19:41:40.885000',19,1),(322,'2024-02-28 19:41:43.837000',16,1),(324,'2024-02-28 19:41:43.861000',12,1),(325,'2024-02-28 19:41:43.868000',8,1),(326,'2024-02-28 19:41:43.875000',8,1),(327,'2024-02-28 19:41:43.879000',8,1),(328,'2024-02-28 19:41:43.883000',8,1),(329,'2024-02-28 19:41:43.890000',8,1),(330,'2024-02-28 19:41:52.114000',8,1),(331,'2024-02-28 19:41:52.122000',8,1),(332,'2024-02-28 19:41:52.125000',8,1),(333,'2024-02-28 19:41:52.129000',8,1),(334,'2024-02-28 19:41:52.133000',8,1),(335,'2024-02-28 19:41:52.138000',8,1),(336,'2024-02-28 19:41:52.145000',8,1),(337,'2024-02-28 19:41:52.149000',8,1),(338,'2024-02-28 19:41:52.154000',8,1),(339,'2024-02-28 19:41:52.157000',8,1),(340,'2024-02-28 19:42:08.237000',2,1);
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (1,'FULL BOARD',2000.00),(2,'HALF BOARD',2500.00),(3,'BED AND BREAKFAST',3000.00);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `user_id` bigint NOT NULL,
  `task_id` int NOT NULL,
  PRIMARY KEY (`task_id`,`user_id`),
  KEY `FKqvd2kxnjgmxr6bpqplu5ikdjk` (`user_id`),
  CONSTRAINT `FK77vwts8l9jfgv48rv33q84k11` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKqvd2kxnjgmxr6bpqplu5ikdjk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(2,1),(2,2),(2,12),(2,15),(2,16),(2,17),(2,19),(2,20),(2,21),(2,23);
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end` datetime(6) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `start` datetime(6) NOT NULL,
  `customer_id` bigint NOT NULL,
  `package_id` int NOT NULL,
  `reservation_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41v6ueo0hiran65w8y1cta2c2` (`customer_id`),
  KEY `FKfqmy6x78fwwhg60teiyupch2e` (`package_id`),
  KEY `FKk3b0evtmta4yj89pcqex60csc` (`reservation_status_id`),
  CONSTRAINT `FK41v6ueo0hiran65w8y1cta2c2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKfqmy6x78fwwhg60teiyupch2e` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`),
  CONSTRAINT `FKk3b0evtmta4yj89pcqex60csc` FOREIGN KEY (`reservation_status_id`) REFERENCES `reservation_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,'2024-03-09 00:00:00.000000',9000.00,'2024-03-02 00:00:00.000000',5,1,4),(2,'2024-03-20 00:00:00.000000',12000.00,'2024-03-12 00:00:00.000000',1,1,3),(3,'2024-03-06 00:00:00.000000',5000.00,'2024-03-06 00:00:00.000000',3,3,4),(4,'2024-02-28 00:00:00.000000',3000.00,'2024-02-28 00:00:00.000000',2,1,1),(5,'2024-03-02 00:00:00.000000',7500.00,'2024-03-01 00:00:00.000000',4,2,5);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation_status`
--

DROP TABLE IF EXISTS `reservation_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation_status`
--

LOCK TABLES `reservation_status` WRITE;
/*!40000 ALTER TABLE `reservation_status` DISABLE KEYS */;
INSERT INTO `reservation_status` VALUES (1,'SHOW'),(2,'NO SHOW'),(3,'PENDING'),(4,'CONFIRMED'),(5,'CANCELLED'),(6,'DELETED');
/*!40000 ALTER TABLE `reservation_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserved_room`
--

DROP TABLE IF EXISTS `reserved_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserved_room` (
  `check_in` datetime(6) DEFAULT NULL,
  `check_out` datetime(6) DEFAULT NULL,
  `room_id` bigint NOT NULL,
  `reservation_id` bigint NOT NULL,
  PRIMARY KEY (`reservation_id`,`room_id`),
  KEY `FKp2gdpag79qoje8fo2ilofmuy6` (`room_id`),
  CONSTRAINT `FKp2gdpag79qoje8fo2ilofmuy6` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `FKq01lh0svrpkadmlbxjy4xb4h3` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserved_room`
--

LOCK TABLES `reserved_room` WRITE;
/*!40000 ALTER TABLE `reserved_room` DISABLE KEYS */;
INSERT INTO `reserved_room` VALUES (NULL,NULL,1,1),(NULL,NULL,2,1),(NULL,NULL,7,2),(NULL,NULL,6,3),('2024-02-28 19:39:19.580000','2024-02-28 19:39:41.252000',3,4),(NULL,'2024-02-28 19:41:47.054000',8,5);
/*!40000 ALTER TABLE `reserved_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `floor` int NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `room_no` int NOT NULL,
  `room_category_id` int NOT NULL,
  `room_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKocdxt1pofv48gxqc73v5g8rj3` (`room_category_id`),
  KEY `FK31b55s8e20hpyopjockpvqf6k` (`room_status_id`),
  CONSTRAINT `FK31b55s8e20hpyopjockpvqf6k` FOREIGN KEY (`room_status_id`) REFERENCES `room_status` (`id`),
  CONSTRAINT `FKocdxt1pofv48gxqc73v5g8rj3` FOREIGN KEY (`room_category_id`) REFERENCES `room_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,1,1500.00,1,1,2),(2,1,1500.00,2,1,2),(3,1,1000.00,3,2,1),(4,1,1000.00,4,2,1),(5,2,5000.00,5,3,1),(6,2,3000.00,6,4,2),(7,2,7000.00,7,5,2),(8,2,4000.00,8,6,1),(9,2,3000.00,9,1,1),(10,2,2000.00,10,2,1);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_category`
--

DROP TABLE IF EXISTS `room_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ac` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `no_of_beds` int NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_category`
--

LOCK TABLES `room_category` WRITE;
/*!40000 ALTER TABLE `room_category` DISABLE KEYS */;
INSERT INTO `room_category` VALUES (1,_binary '','SINGLE BED AC',1,2000.00),(2,_binary '\0','SINGLE BED NO AC',1,1000.00),(3,_binary '','DOUBLE BED AC',2,4000.00),(4,_binary '\0','DOUBLE BED NO AC',2,2000.00),(5,_binary '','TRIPPLE BED AC',3,6000.00),(6,_binary '\0','TRIPPLE BED NO AC',3,3000.00);
/*!40000 ALTER TABLE `room_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_status`
--

DROP TABLE IF EXISTS `room_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_status`
--

LOCK TABLES `room_status` WRITE;
/*!40000 ALTER TABLE `room_status` DISABLE KEYS */;
INSERT INTO `room_status` VALUES (1,'AVAILABLE'),(2,'RESERVED'),(3,'OCCUPIED'),(4,'MAINTAINANCE'),(5,'DELETED');
/*!40000 ALTER TABLE `room_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'LOGIN'),(2,'LOGOUT'),(3,'CREATE USER'),(4,'READ USER'),(5,'UPDATE USER'),(6,'DELETE USER'),(7,'CREATE ROOM CATEGORY'),(8,'READ ROOM CATEGORY'),(9,'UPDATE ROOM CATEGORY'),(10,'DELETE ROOM CATEGORY'),(11,'CREATE ROOM'),(12,'READ ROOM'),(13,'UPDATE ROOM'),(14,'DELETE ROOM'),(15,'CREATE CUSTOMER'),(16,'READ CUSTOMER'),(17,'UPDATE CUSTOMER'),(18,'DELETE CUSTOMER'),(19,'CREATE RESERVATION'),(20,'READ RESERVATION'),(21,'UPDATE RESERVATION'),(22,'DELETE RESERVATION'),(23,'CANCEL RESERVATION');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_title` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
  KEY `FKo6g0t5ih8a5bsioca8qh5ukg3` (`user_status_id`),
  CONSTRAINT `FKo6g0t5ih8a5bsioca8qh5ukg3` FOREIGN KEY (`user_status_id`) REFERENCES `user_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ADMIN','SUPER','ADMIN','password','admin',1),(2,'Receptionist','Kamala','Edirisinghe','1234','kamala',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(35) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (1,'ACTIVE'),(2,'DELETE');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-28 20:08:34
