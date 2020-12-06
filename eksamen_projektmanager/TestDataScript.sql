-- -----------------------------------------------------
-- user table data
-- -----------------------------------------------------

INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test1', '1', 'Nicolai');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test2', '1', 'Phuc');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test3', '1', 'Maja');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test4', '1', 'Bo');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test5', '1', 'Hans');

-- -----------------------------------------------------
-- project table data
-- -----------------------------------------------------
INSERT INTO `projektello`.`projects` (`projectid`, `projectname`) VALUES ('1', 'projekt 1');
INSERT INTO `projektello`.`projects` (`projectid`, `projectname`) VALUES ('2', 'andet projekt');

-- -----------------------------------------------------
-- sub project data
-- -----------------------------------------------------
INSERT INTO `projektello`.`subprojects` (`subprojectid`, `projectid`, `subprojectname`) VALUES ('1', '1', 'mini projekt 1');
INSERT INTO `projektello`.`subprojects` (`subprojectid`, `projectid`, `subprojectname`) VALUES ('2', '1', 'mini projekt 2');
INSERT INTO `projektello`.`subprojects` (`subprojectid`, `projectid`, `subprojectname`) VALUES ('3', '1', 'mini projekt 3');
INSERT INTO `projektello`.`subprojects` (`subprojectid`, `projectid`, `subprojectname`) VALUES ('4', '2', 'mini projekt 4');

-- -----------------------------------------------------
-- tasks data
-- -----------------------------------------------------
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('1', '1', '2', 'opgave 1', '20', '2021-02-02');
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('2', '1', '1', 'opgave 2', '5', '2021-02-02');
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('3', '1', '3', 'opgave 3', '12', '2021-02-02');
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('4', '1', '1', 'opgave 4', '2', '2021-02-02');
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('5', '1', '1', 'opgave 5', '25', '2021-02-02');
INSERT INTO `projektello`.`tasks` (`taskid`, `projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`) VALUES ('6', '2', '4', 'opgave 1', '30', '2021-09-05');

-- -----------------------------------------------------
-- teams data
-- -----------------------------------------------------
INSERT INTO `projektello`.`teams` (`teamid`, `teamname`) VALUES ('1', 'hold 1');
INSERT INTO `projektello`.`teams` (`teamid`, `teamname`) VALUES ('2', 'hold 2');

-- -----------------------------------------------------
-- teamrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`teamrelations` (`teamrelationid`, `userid`, `teamid`) VALUES ('1', '1', '1');
INSERT INTO `projektello`.`teamrelations` (`teamrelationid`, `userid`, `teamid`) VALUES ('2', '2', '1');
INSERT INTO `projektello`.`teamrelations` (`teamrelationid`, `userid`, `teamid`) VALUES ('3', '3', '2');

-- -----------------------------------------------------
-- projectrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`projectrelations` (`projectrelationid`, `userid`, `projectid`) VALUES ('1', '1', '1');
INSERT INTO `projektello`.`projectrelations` (`projectrelationid`, `userid`, `projectid`) VALUES ('2', '1', '2');
INSERT INTO `projektello`.`projectrelations` (`projectrelationid`, `userid`, `projectid`) VALUES ('3', '2', '2');

-- -----------------------------------------------------
-- taskrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`taskrelations` (`taskrelationid`, `taskid`, `userid`) VALUES ('1', '1', '1');
INSERT INTO `projektello`.`taskrelations` (`taskrelationid`, `taskid`, `userid`) VALUES ('2', '2', '1');
INSERT INTO `projektello`.`taskrelations` (`taskrelationid`, `taskid`, `userid`) VALUES ('3', '3', '2');
INSERT INTO `projektello`.`taskrelations` (`taskrelationid`, `taskid`, `userid`) VALUES ('4', '1', '2');


