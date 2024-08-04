CREATE TABLE `bill_record` (
  `id` varchar(255) NOT NULL,
  `bill_amount` double DEFAULT NULL,
  `due_amount` double DEFAULT NULL,
  `last_updated_by` bigint NOT NULL,
  `last_updated_on` datetime(6) NOT NULL,
  `logged_by` bigint NOT NULL,
  `logged_date` datetime(6) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `rowstate` int DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `customer_details_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9744auvncwbmsiitxbsm4gmdu` (`customer_details_id`),
  KEY `logged_by_idx` (`logged_by`),
  KEY `bill_last_updated_by_idx` (`last_updated_by`),
  CONSTRAINT `bill_last_updated_by` FOREIGN KEY (`last_updated_by`) REFERENCES `employee_details` (`id`),
  CONSTRAINT `bill_logged_by` FOREIGN KEY (`logged_by`) REFERENCES `employee_details` (`id`),
  CONSTRAINT `FK9744auvncwbmsiitxbsm4gmdu` FOREIGN KEY (`customer_details_id`) REFERENCES `customer_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer_details` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `last_updated_by` bigint NOT NULL,
  `last_updated_on` datetime(6) NOT NULL,
  `logged_by` bigint NOT NULL,
  `logged_date` datetime(6) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `rowstate` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `logged_by_idx` (`logged_by`),
  KEY `last_updated_by_idx` (`last_updated_by`),
  CONSTRAINT `last_updated_by` FOREIGN KEY (`last_updated_by`) REFERENCES `employee_details` (`id`),
  CONSTRAINT `logged_by` FOREIGN KEY (`logged_by`) REFERENCES `employee_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `due_record` (
  `id` bigint NOT NULL,
  `due_amount` double DEFAULT NULL,
  `last_due_amount` double DEFAULT NULL,
  `last_updated_by` bigint NOT NULL,
  `last_updated_on` datetime(6) NOT NULL,
  `logged_by` bigint NOT NULL,
  `logged_date` datetime(6) NOT NULL,
  `rowstate` int DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4du8kno27gwekun5vbms9qtje` (`customer_id`),
  KEY `logged_by_idx` (`logged_by`),
  KEY `last_updated_by_idx` (`last_updated_by`),
  CONSTRAINT `due_last_updated_by` FOREIGN KEY (`last_updated_by`) REFERENCES `employee_details` (`id`),
  CONSTRAINT `due_logged_by` FOREIGN KEY (`logged_by`) REFERENCES `employee_details` (`id`),
  CONSTRAINT `FKk7ugibs4t8tqd5cnriw16t5ey` FOREIGN KEY (`customer_id`) REFERENCES `customer_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `inventory` (
  `id` varchar(255) NOT NULL,
  `additional_info` varchar(255) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `cgst` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `last_updated_by` bigint NOT NULL,
  `last_updated_on` datetime(6) NOT NULL,
  `logged_by` bigint NOT NULL,
  `logged_date` datetime(6) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `rowstate` int DEFAULT NULL,
  `sgst` double DEFAULT NULL,
  `threshold_quantity` int DEFAULT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  `mrp` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhl05voeludf1teqqpc64pwigt` (`category_id`),
  CONSTRAINT `FKhl05voeludf1teqqpc64pwigt` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `role_activity` (
  `id` bigint NOT NULL,
  `activity_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role_activity_mapping` (
  `id` bigint NOT NULL,
  `activity_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK39d95b22js2u4yr0da1w7n08o` (`activity_id`),
  KEY `FK9o5gfbiu4kvtps0fxpqmowav0` (`role_id`),
  CONSTRAINT `FK39d95b22js2u4yr0da1w7n08o` FOREIGN KEY (`activity_id`) REFERENCES `role_activity` (`id`),
  CONSTRAINT `FK9o5gfbiu4kvtps0fxpqmowav0` FOREIGN KEY (`role_id`) REFERENCES `role_master` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role_master` (
  `id` bigint NOT NULL,
  `role_name` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `logged_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='This table stores about the roles of employee';

CREATE TABLE `employee_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `employee_name` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `designation` varchar(255) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `logged_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated_on` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rowstate` int DEFAULT '1',
  `is_approved` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `role_id_fk_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role_master` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci COMMENT='Employee Details table';


