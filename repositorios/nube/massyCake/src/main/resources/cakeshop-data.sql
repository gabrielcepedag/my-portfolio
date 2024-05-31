-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: mysql
-- ------------------------------------------------------
-- Server version	8.0.26-google

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
-- Current Database: `reposteria`
--


CREATE DATABASE /*!32312 IF NOT EXISTS*/ `reposteria` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `reposteria`;

# SELECT CONCAT("ALTER DEFINER=gabriel@localhost VIEW ", table_name, " AS ", view_definition, ";") FROM information_schema.views WHERE table_schema='reposteria';
SELECT CONCAT("ALTER DEFINER=root@localhost VIEW ", table_name, " AS ", view_definition, ";") FROM information_schema.views WHERE table_schema='reposteria';

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cliente_id` bigint NOT NULL AUTO_INCREMENT,
  `cedula` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `eliminado` tinyint(1) DEFAULT '0',
  `fecha_creacion` date DEFAULT NULL,
  PRIMARY KEY (`cliente_id`),
  UNIQUE KEY `UK_trfs6xemfuo1u29blh0jw3ekl` (`cedula`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
-- INSERT INTO `cliente` VALUES
-- (1,'40214609709','La Vega','Gabriel Cepeda','8295151017',0,now()),
-- (2,'402-3829384-8','','Elias Soliman','8095555555',0, now())
-- ,(3,'40238293848','','John Perez','111111111',0,now()),
-- (5,NULL,'a','a','a',1,now()),(8,NULL,'a','d','a',1,now()),
-- (9,'402-11111111-1','Av. Mirasol, con un ojo mira al mar y contro al sol','Pereira Lopez','8091111111',0,now()),
-- (10,'402-0000000-2','Av. Mirasol, con un ojo mira al mar y contro al sol','Katiuska Mejia','8091111112',0,now()),
-- (11,NULL,'a','Danilo Medina','8091111113',1,now()),(12,NULL,'erhb','rere','ijfbwrjfb',1,now()),
-- (13,'402-0000000-5','','Luis Abinader','8091111113',0,now()),
-- (14,NULL,'eee','eee','eeee',1,now()),
-- (15,'402-3829384-8jh  ff vggvfhbt rgt ','Av. Mirasol, con un ojo mira al mar y contro al sol','Leonel','8293225858',0,now())
-- ,(16,NULL,'ww','ww','ww',1, now()),(17,NULL,'hbiu','7987987','ghuiyv',1,now()),
-- (18,NULL,'aa','aa','aa',1, now()),(19,NULL,'f','f','f',1,now()),(20,'shbdifhbsdf','Av. Mirasol, con un ojo mira al mar y contro al sol','Hipolito','8091111113',0,now()),(21,'4021223322','La romana','PRUEBA 23','8094979138',0, now()),(22,'40219025389','Puerto plata','Cliente Demo','8090000001',0,now()),(23,'40218525389','San Pedro','Cliente Demo 2','8094973899',0,now());
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `cliente_no_deleted`
--

DROP TABLE IF EXISTS `cliente_no_deleted`;
/*!50001 DROP VIEW IF EXISTS `cliente_no_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `cliente_no_deleted` AS SELECT 
 1 AS `cliente_id`,
 1 AS `cedula`,
 1 AS `direccion`,
 1 AS `nombre`,
 1 AS `telefono`,
 1 AS `eliminado`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `precioTotal` decimal(12,2) DEFAULT NULL,
  `precioNeto` decimal(12,2) DEFAULT NULL,
  `descuentoPorProducto` decimal(12,2) NOT NULL,
  `fueVendido` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversion_unidades`
--

DROP TABLE IF EXISTS `conversion_unidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversion_unidades` (
  `conversion_unidades_id` bigint NOT NULL AUTO_INCREMENT,
  `activa` tinyint(1) DEFAULT '1',
  `factor` float NOT NULL,
  `unidad_equivalente` varchar(255) DEFAULT NULL,
  `unidad_origen` varchar(255) DEFAULT NULL,
  `tipo` varchar(20) DEFAULT 'cualquiera',
  PRIMARY KEY (`conversion_unidades_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversion_unidades`
--

LOCK TABLES `conversion_unidades` WRITE;
/*!40000 ALTER TABLE `conversion_unidades` DISABLE KEYS */;
INSERT INTO `conversion_unidades` VALUES (1,1,0.035274,'Oz (masa)','Gr','masa'),(2,1,0.00422675,'Taza','Gr','masa'),(3,1,0.033814,'Oz (liquida)','Ml','liquido'),(4,1,0.033814,'Cucharada','Ml','liquido'),(5,1,14.7868,'Ml','Cucharada','liquido'),(6,1,28.3495,'Gr','Oz (masa)','masa'),(7,1,29.5735,'Ml','Oz (liquida)','liquido'),(8,1,8.11537,'Taza','Oz (liquida)','liquido'),(9,1,4.92892,'Ml','Cucharadita','liquido'),(10,1,453.592,'Gr','Lb','peso'),(11,1,453.592,'Lb','Oz (masa)','peso'),(12,1,2.20462,'Kg','Lb','peso'),(13,1,24,'Paire','Unds','porcion'),(14,1,0.00220462,'Lb','Gr','masa'),(15,1,1000,'Gr','Kg','masa'),(16,1,2.20462,'Lb','Kg','peso');
/*!40000 ALTER TABLE `conversion_unidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_factura`
--

DROP TABLE IF EXISTS `detalle_factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_factura` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `precio_unitario` float DEFAULT NULL,
  `subtotal` float DEFAULT NULL,
  `factura_id` bigint DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKucgyfvfujw8g2tt3c6fdkxai` (`factura_id`),
  KEY `FKdch2fu45yordkhnsqsgrfkn3u` (`producto_id`),
  CONSTRAINT `FKdch2fu45yordkhnsqsgrfkn3u` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`),
  CONSTRAINT `FKucgyfvfujw8g2tt3c6fdkxai` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`factura_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_factura`
--

LOCK TABLES `detalle_factura` WRITE;
/*!40000 ALTER TABLE `detalle_factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `detalle_pedido_id` bigint NOT NULL AUTO_INCREMENT,
  `costo` float DEFAULT NULL,
  `forma` varchar(255) DEFAULT NULL,
  `masa` varchar(255) DEFAULT NULL,
  `nota` varchar(255) DEFAULT NULL,
  `peso` float DEFAULT NULL,
  `relleno` varchar(255) DEFAULT NULL,
  `unidad` varchar(255) DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  PRIMARY KEY (`detalle_pedido_id`),
  KEY `FKgqvba9e7dildyw45u0usdj1k2` (`pedido_id`),
  CONSTRAINT `FKgqvba9e7dildyw45u0usdj1k2` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`pedido_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
-- INSERT INTO `detalle_pedido` VALUES (1,0,'forma1','masa1',NULL,1,'relleno1','Lb',22),(2,0,'forma2','masa2',NULL,2,'relleno2','Lb',22),(3,0,'forma1','masa1',NULL,1,'relleno1','Lb',23),(4,0,'Cuadrados','Chocolate',NULL,10,'Chocolate ','Unds',24),(5,0,'Cuadrados','Chocolate',NULL,10,'Chocolate ','Unds',25),(6,0,'Redonda','Vainilla',NULL,2,'Caramelo','Lb',26),(7,0,'Redonda','Chocolate',NULL,1,'Chocolate','Lb',26),(8,0,'Redondo','Vainilla',NULL,2,'Caramelo','Lb',27),(9,0,'Redondo','Chocolate',NULL,1,'Chocolate','Lb',27),(10,0,'Demo','Demo',NULL,2,'Demo','Lb',28);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distribuidor`
--

DROP TABLE IF EXISTS `distribuidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `distribuidor` (
  `idDistribuidor` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idDistribuidor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribuidor`
--

LOCK TABLES `distribuidor` WRITE;
/*!40000 ALTER TABLE `distribuidor` DISABLE KEYS */;
/*!40000 ALTER TABLE `distribuidor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `empleado_id` bigint NOT NULL AUTO_INCREMENT,
  `admin` tinyint(1) DEFAULT '0',
  `apellido` varchar(255) DEFAULT NULL,
  `cedula` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `eliminado` tinyint(1) DEFAULT '0',
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `rol` varchar(15) NOT NULL,
  PRIMARY KEY (`empleado_id`),
  UNIQUE KEY `UK_elgnbqcwg1gv4h713ytod8n0f` (`cedula`),
  UNIQUE KEY `UK_oqf74jqhm1ebgyhxm1hpi47a1` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;


/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (2,0,'cashier','0000000001','address 1',0,'cashier','cashier','00000000','cashier','CAJERO'),(1,1,'admin','00000000','La Vega',0,'admin','admin','0000000000','admin', 'ADMIN'),(3,0,'chef','00000002','Santiago',0,'chef','chef','0000000000','chef','REPOSTERO');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `empleado_no_deleted`
--

DROP TABLE IF EXISTS `empleado_no_deleted`;
/*!50001 DROP VIEW IF EXISTS `empleado_no_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `empleado_no_deleted` AS SELECT 
 1 AS `empleado_id`,
 1 AS `admin`,
 1 AS `apellido`,
 1 AS `cedula`,
 1 AS `direccion`,
 1 AS `eliminado`,
 1 AS `nombre`,
 1 AS `password`,
 1 AS `telefono`,
 1 AS `rol`,
 1 AS `usuario`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `factura_id` bigint NOT NULL AUTO_INCREMENT,
  `descuento` float DEFAULT NULL,
  `esacredito` bit(1) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `total` float DEFAULT NULL,
  `cliente_id` bigint NOT NULL,
  `empleado_id` bigint NOT NULL,
  PRIMARY KEY (`factura_id`),
  KEY `FK2602efsrpmevi8yxg464stfn5` (`cliente_id`),
  KEY `FK1en00451r95q7effigq0vd1ma` (`empleado_id`),
  CONSTRAINT `FK1en00451r95q7effigq0vd1ma` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`empleado_id`),
  CONSTRAINT `FK2602efsrpmevi8yxg464stfn5` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingrediente_receta`
--

DROP TABLE IF EXISTS `ingrediente_receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingrediente_receta` (
  `receta_id` bigint NOT NULL,
  `cantidad` float NOT NULL,
  `producto_id` bigint NOT NULL,
  `unidad_cantidad` varchar(255) DEFAULT NULL,
  KEY `FK96pr84fsfncwquuk6fwoxcmc2` (`producto_id`),
  KEY `FKd789xl9q0ljewdsu5r8ualqr5` (`receta_id`),
  CONSTRAINT `FK96pr84fsfncwquuk6fwoxcmc2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`),
  CONSTRAINT `FKd789xl9q0ljewdsu5r8ualqr5` FOREIGN KEY (`receta_id`) REFERENCES `receta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingrediente_receta`
--

LOCK TABLES `ingrediente_receta` WRITE;
/*!40000 ALTER TABLE `ingrediente_receta` DISABLE KEYS */;
-- INSERT INTO `ingrediente_receta` VALUES (35,10,7,'Gr'),(34,10,12,'Lb'),(34,5,6,'Unds'),(27,100,14,'Ml'),(27,5,6,'Unds'),(42,10,14,'Oz (liquida)'),(42,10,6,'Unds'),(23,10,14,'Oz (liquida)'),(43,120,12,'Gr'),(43,5,7,'Gr'),(43,75,2,'Gr'),(30,5,1,'Unds'),(24,5,1,'Unds'),(44,500,12,'Gr'),(44,5,6,'Unds'),(45,5,1,'Unds'),(46,5,6,'Unds'),(47,25,13,'Gr'),(47,2,14,'Oz (liquida)');
/*!40000 ALTER TABLE `ingrediente_receta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_compra`
--

DROP TABLE IF EXISTS `orden_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden_compra` (
  `orden_compra_id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `eliminada` tinyint(1) DEFAULT '0',
  `estado` varchar(255) DEFAULT NULL,
  `fecha_generada` datetime DEFAULT NULL,
  `fecha_procesada` datetime DEFAULT NULL,
  `fecha_recibida` datetime DEFAULT NULL,
  `precio_individual` float DEFAULT NULL,
  `producto_id` bigint NOT NULL,
  PRIMARY KEY (`orden_compra_id`),
  KEY `FKpm0ih015u51ulhbxg7ngqd4xc` (`producto_id`),
  CONSTRAINT `FKpm0ih015u51ulhbxg7ngqd4xc` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_compra`
--

LOCK TABLES `orden_compra` WRITE;
/*!40000 ALTER TABLE `orden_compra` DISABLE KEYS */;
-- INSERT INTO `orden_compra` VALUES (4,0,0,'Recibida','2023-07-03 09:19:45','2023-07-13 14:01:01','2023-07-13 22:31:34',NULL,1),(6,0,0,'Procesada','2023-07-03 21:04:57','2023-07-13 14:01:13',NULL,NULL,1),(7,3,0,'Procesada','2023-07-04 10:28:39','2023-07-13 18:53:05',NULL,NULL,1),(8,NULL,0,'Generada','2023-07-18 15:41:00',NULL,NULL,NULL,2);
/*!40000 ALTER TABLE `orden_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `pedido_id` bigint NOT NULL AUTO_INCREMENT,
  `costo_total` float DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `eliminado` tinyint(1) DEFAULT '0',
  `estado` varchar(255) DEFAULT NULL,
  `fecha_despachado` datetime DEFAULT NULL,
  `fecha_entrega` datetime DEFAULT NULL,
  `fecha_orden` datetime DEFAULT NULL,
  `inicial` float DEFAULT NULL,
  `metodo_entrega` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo_pedido` varchar(255) DEFAULT NULL,
  `cliente_id` bigint NOT NULL,
  `empleado_id` bigint NOT NULL,
  PRIMARY KEY (`pedido_id`),
  KEY `FK30s8j2ktpay6of18lbyqn3632` (`cliente_id`),
  KEY `FK1nibrtel55qwnf6rwabwsqkyi` (`empleado_id`),
  CONSTRAINT `FK1nibrtel55qwnf6rwabwsqkyi` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`empleado_id`),
  CONSTRAINT `FK30s8j2ktpay6of18lbyqn3632` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`cliente_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
-- INSERT INTO `pedido` VALUES (1,0,'gsfngjnsdf',0,1,'Registrado',NULL,'2023-06-25 00:00:00','2023-06-25 00:00:00',0,NULL,'Personalizado','Pedido personalizado',1,1),(2,100,'una descripcion',6,1,'Registrado',NULL,'2024-06-29 00:00:00','2024-06-26 00:00:00',100,NULL,'Personalizado','Pedido personalizado',1,1),(3,50,'otra descripcion',25,1,'Registrado',NULL,'2024-06-30 00:00:00','2024-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(4,500,'Pedido #9999',10,1,'Registrado',NULL,'2023-06-30 00:00:00','2023-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(5,142,'Pedido #84',5,1,'Registrado',NULL,'2023-06-30 00:00:00','2023-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(6,75,'gabriel el mejor',15,1,'Registrado',NULL,'2023-06-30 00:00:00','2023-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(7,125,'gabriel el mejor x2',15,1,'Registrado',NULL,'2023-06-30 00:00:00','2023-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(8,55,'Pedido #85',0,1,'Registrado',NULL,'2023-06-30 00:00:00','2023-06-26 00:00:00',500,NULL,'Personalizado','Pedido personalizado',1,1),(9,0,'sfdfghgfssfsf',34,0,'Registrado',NULL,'2023-06-29 00:00:00','2023-06-29 00:00:00',0,NULL,'Personalizado','Pedido personalizado',1,1),(10,0,'fbishisrfhgiefgi',343,1,'Registrado',NULL,NULL,'2023-06-29 00:00:00',0,NULL,'Personalizado','Pedido personalizado',1,1),(11,0,'vf',434,1,'Registrado',NULL,'2023-06-29 00:00:00','2023-06-29 00:00:00',0,NULL,'Personalizado','Pedido personalizado',2,1),(12,0,'Pastel de chocolate de 3 capas',20,1,'Registrado',NULL,'2023-06-29 00:00:00','2023-06-29 00:00:00',0,NULL,'Personalizado','Pedido personalizado',9,1),(13,0,'mmg',34,1,'Registrado',NULL,'2023-06-30 09:24:00','2023-06-30 09:26:34',0,NULL,'Personalizado','Pedido personalizado',1,1),(14,0,'soy una descripcion',12,0,'Registrado',NULL,'2023-07-19 10:00:00','2023-06-30 09:54:32',0,NULL,'Personalizado','Pedido personalizado',2,1),(21,0,'descripcion2',15,0,'Registrado',NULL,'2023-06-30 09:57:00','2023-06-30 10:31:58',0,NULL,'otro pedido','Pedido personalizado',21,1),(22,0,'asdasd',11,0,'Registrado',NULL,'2023-06-30 10:33:00','2023-06-30 10:34:16',0,NULL,'zxczxc','Pedido personalizado',3,1),(23,0,'asdasdasd',13,0,'Registrado',NULL,'2023-06-30 10:37:00','2023-06-30 10:37:48',0,NULL,'prueba3','Pedido personalizado',1,1),(24,0,'Prueba de error recetaPedidoDetalle',0,0,'Registrado',NULL,'2023-07-22 20:00:00','2023-07-14 15:25:08',0,NULL,'Testing Best','Pedido personalizado',9,1),(25,0,'Prueba de error recetaPedidoDetalle',0,0,'Registrado',NULL,'2023-07-22 20:00:00','2023-07-14 15:25:09',0,NULL,'Testing Best','Pedido personalizado',9,1),(26,0,'Pedido especial.',0,0,'Registrado',NULL,'2023-07-18 15:41:00','2023-07-18 15:44:08',0,NULL,'Pedido Demo','Pedido personalizado',22,1),(27,0,'Pedido especial para demostracion',0,0,'Registrado',NULL,'2023-07-18 15:57:00','2023-07-18 15:59:33',0,NULL,'Pedido Demo 2','Pedido personalizado',23,1),(28,0,'demodemo',0,0,'Registrado',NULL,'2023-07-18 17:46:00','2023-07-18 17:47:40',0,NULL,'Demo','Pedido personalizado',23,1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido_ingrediente`
--

DROP TABLE IF EXISTS `pedido_ingrediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_ingrediente` (
  `pedido_pedido_id` bigint NOT NULL,
  `cantidad` float NOT NULL,
  `producto_id` bigint NOT NULL,
  `unidad_cantidad` varchar(255) DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  KEY `FK3yjmnkmtqfrj9nfok62adw92c` (`producto_id`),
  KEY `FK3c8s74rc9pi9jf5o81u8n6gcu` (`pedido_pedido_id`),
  CONSTRAINT `FK3c8s74rc9pi9jf5o81u8n6gcu` FOREIGN KEY (`pedido_pedido_id`) REFERENCES `pedido` (`pedido_id`),
  CONSTRAINT `FK3yjmnkmtqfrj9nfok62adw92c` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_ingrediente`
--

LOCK TABLES `pedido_ingrediente` WRITE;
/*!40000 ALTER TABLE `pedido_ingrediente` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido_ingrediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `pedido_no_deleted`
--

DROP TABLE IF EXISTS `pedido_no_deleted`;
/*!50001 DROP VIEW IF EXISTS `pedido_no_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `pedido_no_deleted` AS SELECT 
 1 AS `pedido_id`,
 1 AS `costo_total`,
 1 AS `descripcion`,
 1 AS `descuento`,
 1 AS `eliminado`,
 1 AS `estado`,
 1 AS `fecha_despachado`,
 1 AS `fecha_entrega`,
 1 AS `fecha_orden`,
 1 AS `inicial`,
 1 AS `metodo_entrega`,
 1 AS `nombre`,
 1 AS `tipo_pedido`,
 1 AS `cliente_id`,
 1 AS `empleado_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `producto_id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `disp_min` int DEFAULT NULL,
  `eliminado` tinyint(1) DEFAULT '0',
  `inicial` float DEFAULT NULL,
  `medida` float DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `unidad_medida` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT 'N/E',
  `foto` varchar(255) DEFAULT NULL,
  `precio` float DEFAULT 0,
  PRIMARY KEY (`producto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
-- INSERT INTO `producto` VALUES (1,7,'Láminas de hojaldre de repostería.',0,10,0,0,10,'Hojaldre','Ingrediente','Unds',NULL,NULL,0),
-- (2,1,'Harina blanca de alta calidad. 10 Libras.',0,2,1,0,10,'saco de harina','Ingrediente','Lb',NULL,NULL, 0),
-- (3,130,'dddd',0,12,1,0,12,'vddd','Ingrediente','Lb',NULL,NULL, 0),(4,50,'dfghghg',0,10,1,0,24.5,'ffddgh','Ingrediente','Lb',NULL,NULL, 0),
-- (5,15,'jkhjgkjhk',0,10,1,0,45,'hjfgh','Ingrediente','Lb',NULL,NULL, 0),
-- (6,35,'Paquete de huevos marrones Endy',0,5,0,0,15,'Huevos Marrnoes','Ingrediente','Unds',NULL,NULL, 0),
-- (7,105,'Levadura Description',0,5,0,0,24,'Levadura','Ingrediente','Kg',NULL,NULL, 0),
-- (8,50,'Masa de harina de pan',0,8,0,0,34.5,'Masa de Pan','Ingrediente','Lb',NULL,NULL, 0),
-- (9,11,'2221ds',0,3,1,0,12,'dddd','Ingrediente','Lb',NULL,NULL, 0),(10,22,'dddd',0,1,1,0,22,'dddd','Ingrediente','Lb',NULL,NULL, 0),
-- (11,22,'asdd',0,1,1,0,22,'ssssw','Ingrediente','Lb',NULL,NULL, 0),(12,35,'Sacos de Azucar',NULL,1,0,NULL,1,'Azucar','Ingrediente','Lb',NULL,NULL, 0),
-- (13,19,'Harina',0,1,0,0,97,'Harina','Ingrediente','Gr',NULL,NULL, 0),
-- (14,75,'Pote aceite grande',0,1,0,0,128,'Aceite','Ingrediente','Oz (liquida)',NULL,NULL, 0),
-- (15,1,'Brownies de mar iguana',NULL,NULL,0,NULL,40,'Happy Brownies','Personalizado','Unds',NULL,NULL, 0),
-- (16,10,'Pedazos de Marmol Cake',NULL,5,0,NULL,10,'Marmol Cake','Cabina','Unds',NULL,NULL, 0);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `producto_no_deleted`
--

DROP TABLE IF EXISTS `producto_no_deleted`;
/*!50001 DROP VIEW IF EXISTS `producto_no_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `producto_no_deleted` AS SELECT 
 1 AS `producto_id`,
 1 AS `cantidad`,
 1 AS `descripcion`,
 1 AS `descuento`,
 1 AS `disp_min`,
 1 AS `eliminado`,
 1 AS `inicial`,
 1 AS `medida`,
 1 AS `nombre`,
 1 AS `tipo`,
 1 AS `unidad_medida`,
 1 AS `categoria`,
 1 AS `precio`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `provincia`
--

DROP TABLE IF EXISTS `provincia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincia` (
  `idProvincia` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idProvincia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincia`
--

LOCK TABLES `provincia` WRITE;
/*!40000 ALTER TABLE `provincia` DISABLE KEYS */;
/*!40000 ALTER TABLE `provincia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receta`
--

DROP TABLE IF EXISTS `receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `minutos_preparacion` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `pasos` text,
  `porcion_resultante` int NOT NULL,
  `uni_porcion_resultante` varchar(255) DEFAULT NULL,
  `eliminado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta`
--

LOCK TABLES `receta` WRITE;
/*!40000 ALTER TABLE `receta` DISABLE KEYS */;
-- INSERT INTO `receta` VALUES (23,'Los pasteles de Belém son un dulce a base de hojaldre y crema, cuya receta tradicional es secreta.',45,'Pastéis de Belém','En un cazo a fuego fuerte, pon la leche junto con la nata, el palo de canela y la piel del limón. Cuando empiece a hervir, retíralo del fuego.;Mientras, en un bol pon las yemas de huevo junto con el azúcar y la maicena, y bátelo todo con unas varillas.;Vierte en el bol la leche y la nata infusionadas poco a poco y removiendo a la vez.;De nuevo vierte la mezcla en el cazo que habías utilizado, y ponlo a fuego lento mientras remueves sin parar para que no se te pegue, a la vez que se va espesando.;Ponla en un bol y deja que se temple. Cuando esté a temperatura ambiente, retira la piel del limón y la rama de canela.;Precalienta el horno a 250ºC.;Saca el hojaldre de la nevera, estíralo un poco con un rodillo y córtalo con un cortador de galletas o un vaso, de unos 10 cm de diámetro aproximadamente.\nDeberás obtener 12 círculos.;Engrasa con un poco de mantequilla el molde que vayas a utilizar.;Ahora coloca los 12 círculos de hojaldre en los huecos, presionando los laterales para que no queden ondulados y cubran bien los huecos.;Ve rellenando el interior con la crema que hemos preparado, aproximadamente hasta los 2/3 de capacidad de cada hueco, intentando no llegar al borde por los lados.;Introduce el molde en el horno y hornéalos unos 15-20 minutos, hasta que veas que la superficie está dorada, como puedes ver en las fotografías.;Saca el molde, déjalo enfriar unos minutos y a continuación desmolda los pasteles y déjalos que se enfríen del todo sobre una rejilla.',12,'Gr',0),(24,'esto es una descripcion de gabriel',60,'prueba gabriel',NULL,2,'Gr',0),(27,'Pasteles de harina2',55,'Pasteles2','Hsrina',10,'Gr',0),(28,'Pasteles de Harina 3',96,'Pasteles3','Paso 1',12,'Unds',1),(29,'Pasteles de Harina 3',96,'Pasteles3',NULL,12,'Unds',1),(30,'fhgh',211,'Brownie a la moda',NULL,33,'Gr',0),(31,'Prepara este árbol de Navidad de hojaldre y crema de cacao',50,'Árbol de Navidad de hojaldre',NULL,6,'Gr',0),(32,'test2',345,'test test','Hola;Adios',123,'Lbs',1),(33,'Pasteles de fiesta para 10 personas.',25,'Pasteles de Fiesta',NULL,10,'Gr',1),(34,'esta es una descripcion de prueba',51,'Receta #99',NULL,40,'Gr',0),(35,'otra descripcion',60,'PRUEBA 23','asdadasd;asdasd',10,'Lbs',1),(36,'hgfhfghgf',45,'hgdfh','gfdgdf',545,'Lbs',1),(37,'xcbvb',44,'cvxcv','cxvxcv',4,'Lbs',1),(38,'bbbbb',9888,' bnbnbb',NULL,665,'Lbs',1),(39,'dfggdhgf',55,'dgffg',NULL,55,'Lbs',1),(40,'ssssss',22,'sssss',NULL,22,'Lbs',1),(41,'swwww',11,'ZSDsdas',NULL,22,'Lbs',1),(42,'esto es una descripcion',40,'probando error','voltearte y sacar la hoja',13,'Gr',0),(43,'Sabrosas galletas de mantequilla de mani hechas en casa.',120,'Galletas de Mantequilla de Mani','Mantequilla;De;Manix',24,'Unds',0),(44,'klk mmg',60,'prueba Frank','primero besar el culo;lamer la creta',15,'Unds',0),(45,'Probando errores',999,'Prueba 34','Paso 1',909,'Gr',0),(46,'asdasd',50,'prueba del mejor',NULL,20,'Unds',0),(47,'Masa de vainilla',40,'Masa Vainilla','Echar leche;Echar harina',1,'Lb',0);
/*!40000 ALTER TABLE `receta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `receta_no_deleted`
--

DROP TABLE IF EXISTS `receta_no_deleted`;
/*!50001 DROP VIEW IF EXISTS `receta_no_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `receta_no_deleted` AS SELECT 
 1 AS `id`,
 1 AS `descripcion`,
 1 AS `minutos_preparacion`,
 1 AS `nombre`,
 1 AS `pasos`,
 1 AS `porcion_resultante`,
 1 AS `uni_porcion_resultante`,
 1 AS `eliminado`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `recibo_pago`
--

DROP TABLE IF EXISTS `recibo_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recibo_pago` (
  `recibo_pago_id` bigint NOT NULL AUTO_INCREMENT,
  `abonado` float DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `forma_pago` varchar(255) DEFAULT NULL,
  `restante` float DEFAULT NULL,
  `factura_id` bigint NOT NULL,
  PRIMARY KEY (`recibo_pago_id`),
  KEY `FKhora9gpdiftx3urqqo5p5m6pf` (`factura_id`),
  CONSTRAINT `FKhora9gpdiftx3urqqo5p5m6pf` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`factura_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recibo_pago`
--

LOCK TABLES `recibo_pago` WRITE;
/*!40000 ALTER TABLE `recibo_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `recibo_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `reposteria`
--

USE `reposteria`;

--
-- Final view structure for view `cliente_no_deleted`
--

/*!50001 DROP VIEW IF EXISTS `cliente_no_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `cliente_no_deleted` AS select * from `cliente` `e` where (`e`.`eliminado` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `empleado_no_deleted`
--

/*!50001 DROP VIEW IF EXISTS `empleado_no_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `empleado_no_deleted` AS select * from `empleado` `e` where (`e`.`eliminado` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `pedido_no_deleted`
--

/*!50001 DROP VIEW IF EXISTS `pedido_no_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `pedido_no_deleted` AS select * from `pedido` `e` where (`e`.`eliminado` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `producto_no_deleted`
--

/*!50001 DROP VIEW IF EXISTS `producto_no_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `producto_no_deleted` AS select * from `producto` `e` where (`e`.`eliminado` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `receta_no_deleted`
--

/*!50001 DROP VIEW IF EXISTS `receta_no_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `receta_no_deleted` AS select * from `receta` `e` where (`e`.`eliminado` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-25 19:16:56
