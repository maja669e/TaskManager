-- -----------------------------------------------------
-- Schema projectello
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projektello` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `projektello` ;

-- -----------------------------------------------------
-- Table projects
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`projects` (
  `projectid` INT NOT NULL,
  `projectname` VARCHAR(100) NULL DEFAULT 'nyt projekt',
  `startdate` DATE NULL DEFAULT NULL,
  `enddate` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`projectid`),
  UNIQUE INDEX `projectid_UNIQUE` (`projectid` ASC) VISIBLE)

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`users` (
  `userid` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE INDEX `userid_UNIQUE` (`userid` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table projectrelations
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`projectrelations` (
  `projectrelationid` INT NOT NULL,
  `userid` INT NOT NULL,
  `projectid` INT NOT NULL,
  PRIMARY KEY (`projectrelationid`),
  UNIQUE INDEX `projectrelationid_UNIQUE` (`projectrelationid` ASC) VISIBLE,
  INDEX `userid_idx` (`userid` ASC) VISIBLE,
  INDEX `projectid3_idx` (`projectid` ASC) VISIBLE,
  CONSTRAINT `projectid3`
    FOREIGN KEY (`projectid`)
    REFERENCES `projektello`.`projects` (`projectid`),
  CONSTRAINT `userid2`
    FOREIGN KEY (`userid`)
    REFERENCES `projektello`.`users` (`userid`))

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table subprojects
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`subprojects` (
  `subprojectid` INT NOT NULL,
  `projectid` INT NOT NULL,
  `subprojectname` VARCHAR(100) NOT NULL DEFAULT 'nyt delprojekt',
  PRIMARY KEY (`subprojectid`, `projectid`),
  INDEX `projectid_idx` (`projectid` ASC) VISIBLE,
  CONSTRAINT `projectid`
    FOREIGN KEY (`projectid`)
    REFERENCES `projektello`.`projects` (`projectid`))

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table tasks
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`tasks` (
  `taskid` INT NOT NULL,
  `projectid` INT NOT NULL,
  `subprojectid` INT NOT NULL,
  `taskname` VARCHAR(100) NOT NULL DEFAULT 'ny opgave',
  `timeestimate` INT NULL DEFAULT NULL DEFAULT '0',
  `deadline` DATE NULL DEFAULT NULL DEFAULT '2020-01-01',
  `taskstatus` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`taskid`),
  INDEX `projectid_idx` (`projectid` ASC) VISIBLE,
  INDEX `subprojectid_idx` (`subprojectid` ASC) VISIBLE,
  CONSTRAINT `projectid2`
    FOREIGN KEY (`projectid`)
    REFERENCES `projektello`.`projects` (`projectid`),
  CONSTRAINT `subprojectid`
    FOREIGN KEY (`subprojectid`)
    REFERENCES `projektello`.`subprojects` (`subprojectid`))

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table taskrelations
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`taskrelations` (
  `taskrelationid` INT NOT NULL,
  `taskid` INT NOT NULL,
  `userid` INT NULL DEFAULT NULL,
  PRIMARY KEY (`taskrelationid`),
  UNIQUE INDEX `taskrelationid_UNIQUE` (`taskrelationid` ASC) VISIBLE,
  INDEX `taskid_idx` (`taskid` ASC) VISIBLE,
  INDEX `userid3_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `taskid`
    FOREIGN KEY (`taskid`)
    REFERENCES `projektello`.`tasks` (`taskid`),
  CONSTRAINT `userid3`
    FOREIGN KEY (`userid`)
    REFERENCES `projektello`.`users` (`userid`))

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table teams
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`teams` (
  `teamid` INT NOT NULL,
  `teamname` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`teamid`),
  UNIQUE INDEX `teamid_UNIQUE` (`teamid` ASC) VISIBLE)

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table teamrelations
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektello`.`teamrelations` (
  `teamrelationid` INT NOT NULL,
  `userid` INT NOT NULL,
  `teamid` INT NOT NULL,
  PRIMARY KEY (`teamrelationid`),
  UNIQUE INDEX `teamrelationid_UNIQUE` (`teamrelationid` ASC) VISIBLE,
  INDEX `userid_idx` (`userid` ASC) VISIBLE,
  INDEX `teamid_idx` (`teamid` ASC) VISIBLE,
  CONSTRAINT `teamid`
    FOREIGN KEY (`teamid`)
    REFERENCES `projektello`.`teams` (`teamid`),
  CONSTRAINT `userid`
    FOREIGN KEY (`userid`)
    REFERENCES `projektello`.`users` (`userid`))

ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;