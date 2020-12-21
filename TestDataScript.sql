-- -----------------------------------------------------
-- user table data
-- -----------------------------------------------------

INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test1', '1', 'Nicolai');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test2', '1', 'Phuc');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test3', '1', 'Maja');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test4', '1', 'Bo');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test5', '1', 'Hans');
INSERT INTO `projektello`.`users` (`username`, `password`, `name`) VALUES ('test6', '1', 'Karen');

-- -----------------------------------------------------
-- project table data
-- -----------------------------------------------------
INSERT INTO `projektello`.`projects` (`projectname`,`startdate`,`enddate`) VALUES ('Projekt 1','2020-12-07','2021-02-02');
INSERT INTO `projektello`.`projects` (`projectname`,`startdate`,`enddate`) VALUES ('Projekt 2','2021-12-07','2021-02-02');
INSERT INTO `projektello`.`projects` (`projectname`,`startdate`,`enddate`) VALUES ('Projekt 3','2021-12-07','2021-02-02');

-- -----------------------------------------------------
-- sub project data
-- -----------------------------------------------------
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('1', 'mini projekt 1');
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('1', 'mini projekt 2');
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('2', 'mini projekt 3');
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('2', 'mini projekt 4');
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('3', 'mini projekt 5');
INSERT INTO `projektello`.`subprojects` (`projectid`, `subprojectname`) VALUES ('3', 'mini projekt 6');

-- -----------------------------------------------------
-- tasks data
-- -----------------------------------------------------
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('1', '1', 'opgave 1', '20', '2021-02-02', 2);
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('1', '2', 'opgave 2', '5', '2021-02-02', 2);
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('2', '3', 'opgave 3', '12', '2021-02-02', 1);
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('2', '4', 'opgave 4', '2', '2021-02-02', 0);
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('3', '5', 'opgave 5', '25', '2021-02-02', 1);
INSERT INTO `projektello`.`tasks` (`projectid`, `subprojectid`, `taskname`, `timeestimate`, `deadline`, `taskstatus`) VALUES ('3', '6', 'opgave 6', '30', '2021-09-05', 0);


-- -----------------------------------------------------
-- teams data
-- -----------------------------------------------------
INSERT INTO `projektello`.`teams` (`teamname`) VALUES ('Hold 1');
INSERT INTO `projektello`.`teams` (`teamname`) VALUES ('Hold 2');

-- -----------------------------------------------------
-- teamrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('1', '1');
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('2', '1');
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('3', '1');
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('4', '2');
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('5', '2');
INSERT INTO `projektello`.`teamrelations` (`userid`, `teamid`) VALUES ('6', '2');

-- -----------------------------------------------------
-- projectrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('1', '1');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('1', '2');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('2', '2');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('2', '1');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('3', '2');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('3', '3');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('4', '3');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('4', '2');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('5', '3');
INSERT INTO `projektello`.`projectrelations` (`userid`, `projectid`) VALUES ('6', '3');

-- -----------------------------------------------------
-- taskrelations data
-- -----------------------------------------------------
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('1', '1');
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('2', '1');
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('3', '2');
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('4', '3');
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('4', '6');
INSERT INTO `projektello`.`taskrelations` (`taskid`, `userid`) VALUES ('5', '5');


