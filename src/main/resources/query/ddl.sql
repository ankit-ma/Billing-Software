CREATE TABLE `janta_store`.`role_master` (
  `id` BIGINT(11) NOT NULL,
  `role_name` VARCHAR(100) NOT NULL,
  `logged_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'This table stores about the roles of employee';

CREATE TABLE `janta_store`.`employee_details` (
  `id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL,
  `employee_name` VARCHAR(100) NOT NULL,
  `phone_number` VARCHAR(10) NOT NULL,
  `designation` VARCHAR(45) NOT NULL,
  `logged_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  `rowstate` INT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `role_id_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `role_id_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `janta_store`.`role_master` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci
COMMENT = 'Employee Details table';

