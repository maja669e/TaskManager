        -- -----------------------------------------------------
        -- Schema projectello
        -- -----------------------------------------------------
        CREATE SCHEMA IF NOT EXISTS `projectello`;
        USE `projectello` ;

        -- -----------------------------------------------------
        -- Table `projectello`.`projects`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`projects` (
        `projectid` INT NOT NULL,
        `projectsname` VARCHAR(100) NULL DEFAULT NULL,
        PRIMARY KEY (`projectid`),
        UNIQUE INDEX `projectid_UNIQUE` (`projectid` ASC) VISIBLE)


        -- -----------------------------------------------------
        -- Table `projectello`.`users`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`users` (
        `userid` INT NOT NULL AUTO_INCREMENT,
        `username` VARCHAR(100) NOT NULL,
        `password` VARCHAR(100) NOT NULL,
        `name` VARCHAR(100) NOT NULL,
        PRIMARY KEY (`userid`),
        UNIQUE INDEX `userid_UNIQUE` (`userid` ASC) VISIBLE,
        UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)


        -- -----------------------------------------------------
        -- Table `projectello`.`projectrelations`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`projectrelations` (
        `projectrelationid` INT NOT NULL,
        `userid` INT NOT NULL,
        `projectid` INT NOT NULL,
        PRIMARY KEY (`projectrelationid`),
        UNIQUE INDEX `projectrelationid_UNIQUE` (`projectrelationid` ASC) VISIBLE,
        INDEX `userid_idx` (`userid` ASC) VISIBLE,
        INDEX `projectid3_idx` (`projectid` ASC) VISIBLE,
        CONSTRAINT `projectid3`
        FOREIGN KEY (`projectid`)
        REFERENCES `projectello`.`projects` (`projectid`),
        CONSTRAINT `userid2`
        FOREIGN KEY (`userid`)
        REFERENCES `projectello`.`users` (`userid`))


        -- -----------------------------------------------------
        -- Table `projectello`.`subprojects`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`subprojects` (
        `subprojectid` INT NOT NULL,
        `projectid` INT NOT NULL,
        `subprojectname` VARCHAR(100) NOT NULL DEFAULT 'nyt delprojekt',
        PRIMARY KEY (`subprojectid`, `projectid`),
        INDEX `projectid_idx` (`projectid` ASC) VISIBLE,
        CONSTRAINT `projectid`
        FOREIGN KEY (`projectid`)
        REFERENCES `projectello`.`projects` (`projectid`))


        -- -----------------------------------------------------
        -- Table `projectello`.`tasks`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`tasks` (
        `taskid` INT NOT NULL,
        `projectid` INT NOT NULL,
        `subprojectid` INT NOT NULL,
        `taskname` VARCHAR(100) NOT NULL DEFAULT 'ny opgave',
        PRIMARY KEY (`taskid`),
        INDEX `projectid_idx` (`projectid` ASC) VISIBLE,
        INDEX `subprojectid_idx` (`subprojectid` ASC) VISIBLE,
        CONSTRAINT `projectid2`
        FOREIGN KEY (`projectid`)
        REFERENCES `projectello`.`projects` (`projectid`),
        CONSTRAINT `subprojectid`
        FOREIGN KEY (`subprojectid`)
        REFERENCES `projectello`.`subprojects` (`subprojectid`))
        

        -- -----------------------------------------------------
        -- Table `projectello`.`taskrelations`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`taskrelations` (
        `taskrelationid` INT NOT NULL,
        `taskid` INT NOT NULL,
        `userid` INT NOT NULL,
        PRIMARY KEY (`taskrelationid`),
        UNIQUE INDEX `taskrelationid_UNIQUE` (`taskrelationid` ASC) VISIBLE,
        INDEX `taskid_idx` (`taskid` ASC) VISIBLE,
        INDEX `userid3_idx` (`userid` ASC) VISIBLE,
        CONSTRAINT `taskid`
        FOREIGN KEY (`taskid`)
        REFERENCES `projectello`.`tasks` (`taskid`),
        CONSTRAINT `userid3`
        FOREIGN KEY (`userid`)
        REFERENCES `projectello`.`users` (`userid`))


        -- -----------------------------------------------------
        -- Table `projectello`.`teams`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`teams` (
        `teamid` INT NOT NULL,
        `teamname` VARCHAR(100) NOT NULL,
        PRIMARY KEY (`teamid`),
        UNIQUE INDEX `teamid_UNIQUE` (`teamid` ASC) VISIBLE)


        -- -----------------------------------------------------
        -- Table `projectello`.`teamrelations`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `projectello`.`teamrelations` (
        `teamrelationid` INT NOT NULL,
        `userid` INT NOT NULL,
        `teamid` INT NOT NULL,
        PRIMARY KEY (`teamrelationid`),
        UNIQUE INDEX `teamrelationid_UNIQUE` (`teamrelationid` ASC) VISIBLE,
        INDEX `userid_idx` (`userid` ASC) VISIBLE,
        INDEX `teamid_idx` (`teamid` ASC) VISIBLE,
        CONSTRAINT `teamid`
        FOREIGN KEY (`teamid`)
        REFERENCES `projectello`.`teams` (`teamid`),
        CONSTRAINT `userid`
        FOREIGN KEY (`userid`)
        REFERENCES `projectello`.`users` (`userid`))
