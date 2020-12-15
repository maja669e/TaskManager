package demo.data;

import demo.model.*;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcTestFixture {

    static final String USER = "root";
    static final String PWD = "root";
    static final String URL = "jdbc:mysql://localhost/projektellotest?serverTimezone=UTC"; //needs to make schema in workbench named projektellotest
    Connection connection;

    public void setUp() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            Statement st = connection.createStatement();
            // start transaction
            connection.setAutoCommit(false);

            // create

            st.addBatch("DROP TABLE if exists projects");
            st.addBatch("create table projects(" +
                    "   projectid       integer not null primary key," +
                    "   projectname     varchar(100) not null," +
                    "   startdate       date not null," +
                    "   enddate         date not null)");

            st.addBatch("DROP TABLE if exists users");
            st.addBatch("create table users(" +
                    "   userid      integer not null primary key," +
                    "   username    varchar(100) not null," +
                    "   password    varchar(100) not null," +
                    "   name        varchar(100) not null)");

            st.addBatch("DROP TABLE if exists projectrelations");
            st.addBatch("create table projectrelations(" +
                    "   projectrelationid   integer not null primary key," +
                    "   userid              integer not null," +
                    "   projectid           integer not null) ");

            st.addBatch("DROP TABLE if exists subprojects");
            st.addBatch("create table subprojects(" +
                    "   subprojectid          integer not null primary key," +
                    "   projectid             integer not null," +
                    "   subprojectname        varchar(100)) ");

            st.addBatch("DROP TABLE if exists tasks");
            st.addBatch("create table tasks(" +
                    "   taskid                integer not null primary key," +
                    "   projectid             integer," +
                    "   subprojectid          integer," +
                    "   taskname              varchar(100)," +
                    "   timeestimate          integer," +
                    "   deadline              date, " +
                    "   taskstatus            smallint)");

            st.addBatch("DROP TABLE if exists taskrelations");
            st.addBatch("create table taskrelations(" +
                    "   taskrelationid   integer not null primary key," +
                    "   taskid           integer not null," +
                    "   userid           integer not null) ");

            st.addBatch("DROP TABLE if exists teams");
            st.addBatch("create table teams(" +
                    "   teamid             integer not null primary key," +
                    "   teamname           varchar(100)) ");

            st.addBatch("DROP TABLE if exists teamrelations");
            st.addBatch("create table teamrelations(" +
                    "   teamrelationid   integer not null primary key," +
                    "   userid           integer not null," +
                    "   teamid           integer not null) ");


            // insert
            st.addBatch("INSERT INTO users VALUES (1,'test1', '1', 'Nicolai')");
            st.addBatch("INSERT INTO users VALUES (2,'test2', '1', 'Phuc')");
            st.addBatch("INSERT INTO users VALUES (3,'test3', '1', 'Maja')");
            st.addBatch("INSERT INTO users VALUES (4,'test4', '1', 'Bo')");
            st.addBatch("INSERT INTO users VALUES (5,'test5', '1', 'Hans')");

            st.addBatch("INSERT INTO projects VALUES (1,'projekt 1','2020-01-01','2020-01-01')");
            st.addBatch("INSERT INTO projects VALUES (2,'projekt 2','2020-01-01','2020-01-01')");

            st.addBatch("INSERT INTO projectrelations VALUES (1,1,1)");
            st.addBatch("INSERT INTO projectrelations VALUES (2,1,2)");
            st.addBatch("INSERT INTO projectrelations VALUES (3,2,2)");

            st.addBatch("INSERT INTO subprojects VALUES(1,1,'mini projekt 1')");
            st.addBatch("INSERT INTO subprojects VALUES(2,1,'mini projekt 2')");
            st.addBatch("INSERT INTO subprojects VALUES(3,1,'mini projekt 3')");
            //st.addBatch("INSERT INTO subprojects VALUES(1,2,'mini projekt 4')");

            st.addBatch("INSERT INTO tasks VALUES(1,1,2,'opgave 1', 20, '2021-02-02',1)");
            st.addBatch("INSERT INTO tasks VALUES(2,1,1,'opgave 2', 5, '2021-02-02',0)");
            st.addBatch("INSERT INTO tasks VALUES(3,1,3,'opgave 3', 12, '2021-02-02',0)");
            st.addBatch("INSERT INTO tasks VALUES(4,1,1,'opgave 4', 2, '2021-02-02',2)");
            st.addBatch("INSERT INTO tasks VALUES(5,1,2,'opgave 5', 25, '2021-02-02',2)");
            st.addBatch("INSERT INTO tasks VALUES(6,2,1,'opgave 1', 30, '2021-09-05',3)");

            st.addBatch("INSERT INTO teams VALUES(1,'hold 1')");
            st.addBatch("INSERT INTO teams VALUES(2,'hold 2')");

            st.addBatch("INSERT INTO teamrelations VALUE(1,1,1)");
            st.addBatch("INSERT INTO teamrelations VALUE(2,2,1)");
            st.addBatch("INSERT INTO teamrelations VALUE(3,3,2)");

            st.addBatch("INSERT INTO taskrelations VALUES(1,1,1)");
            st.addBatch("INSERT INTO taskrelations VALUES(2,2,1)");
            st.addBatch("INSERT INTO taskrelations VALUES(3,3,2)");
            st.addBatch("INSERT INTO taskrelations VALUES(4,1,2)");

            int[] updateCounts = st.executeBatch();

            // end transaction
            connection.commit();
        } catch (Exception e) {
            System.out.println("Fail in JdbcTestFixture - setup");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public void tearDown() throws SQLException {
        connection.close();
    }

    public void addProject() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            Statement st = connection.createStatement();
            // start transaction
            connection.setAutoCommit(false);

            st.addBatch("INSERT INTO projects VALUES (3,'projekt 3','2020-01-01','2020-01-01')");

            st.addBatch("INSERT INTO projectrelations VALUES (4,1,3)");

            int[] updateCounts = st.executeBatch();

            // end transaction
            connection.commit();

        } catch (Exception e) {
            System.out.println("Fail to add project to db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public Project getProject(int projectid) throws SQLException {
        List<SubProject> subProjects = new ArrayList<>();
        List<User> tempUserList = new ArrayList<>();
        Project project = new Project();
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            String SQL = "SELECT * FROM projects left join subprojects ON projects.projectid = subprojects.projectid " +
                    "left join tasks ON subprojects.subprojectid = tasks.subprojectid " +
                    "left join taskrelations ON tasks.taskid = taskrelations.taskid " +
                    "left join users ON taskrelations.userid = users.userid where projects.projectid = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, projectid);
            ResultSet rs = ps.executeQuery();

            // start transaction
            connection.setAutoCommit(false);

            int formerTaskid = 0;

            while (rs.next()) {
                //Formatter
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                //Project
                String projectName = rs.getString("projectname");
                String startDateTemp = rs.getString("startdate");
                LocalDate startDate = LocalDate.parse(startDateTemp, formatter);

                String endDateTemp = rs.getString("enddate");
                LocalDate endDate = LocalDate.parse(endDateTemp, formatter);

                project.setProjectName(projectName);
                project.setExpStartDate(startDate);
                project.setExpEndDate(endDate);
                project.setProjectid(projectid);
                project.setSubProjects(subProjects);

                //Subproject
                int subprojectid = rs.getInt("subprojectid");
                String subprojectName = rs.getString("subprojectname");

                SubProject subProject = new SubProject(subprojectName);
                subProject.setSubProjectID(subprojectid);

                boolean subidInProject = false;

                if (subProject.getSubProjectID() != 0) {
                    if (!subProjects.isEmpty()) {
                        for (SubProject subProjectObj : project.getSubProjects()) {
                            if (subProjectObj.equals(subProject)) {
                                subidInProject = true;
                            }
                        }
                        if (!subidInProject) {
                            project.getSubProjects().add(subProject);
                            List<Task> tasks = new ArrayList<>();
                            subProject.setTasks(tasks);
                        }
                    } else {
                        project.getSubProjects().add(subProject);
                        List<Task> tasks = new ArrayList<>();
                        subProject.setTasks(tasks);
                    }
                }

                //Task
                String temp = rs.getString("deadline");
                int taskid = rs.getInt("taskid");
                String taskname = rs.getString("taskname");
                int timeEstimation = rs.getInt("timeestimate");
                int taskstatus = rs.getInt("taskstatus");

                if (temp == null && taskname == null) {
                    taskname = "temp";
                    temp = "2021-02-02"; // temp
                }

                LocalDate deadline = LocalDate.parse(temp, formatter);

                Task task = new Task(deadline, timeEstimation, taskname);
                task.setTaskId(taskid);
                task.setTaskStatus(taskstatus);
                task.setTaskMembers(tempUserList);

                //Task User
                int userid = rs.getInt("userid");
                String username = rs.getString("username");
                String name = rs.getString("name");

                User taskUser = new User(username, name);
                taskUser.setUserid(userid);

                if (taskUser.getName() == null && taskUser.getUserName() == null) {
                    taskUser.setName("temp name");
                    taskUser.setUserName("temp username");
                }

                boolean userInTask = false;
                if (taskUser.getUserid() != 0) {
                    if (!task.getTaskMembers().isEmpty()) {
                        for (User userObj : task.getTaskMembers()) {
                            if (userObj.equals(taskUser)) {
                                userInTask = true;
                            }
                        }
                        if (!userInTask) {
                            if (formerTaskid != taskid) {
                                List<User> taskUsers = new ArrayList<>();
                                task.setTaskMembers(taskUsers);
                            }
                            task.getTaskMembers().add(taskUser);
                        }
                    } else {
                        List<User> taskUsers = new ArrayList<>();
                        task.setTaskMembers(taskUsers);
                        task.getTaskMembers().add(taskUser);
                    }
                }

                formerTaskid = taskid;

                boolean taskidInSubProject = false;
                if (task.getTaskId() != 0) {
                    for (SubProject subProjectObj : project.getSubProjects()) {
                        if (subProjectObj.getSubProjectID() == subprojectid) {
                            if (!subProjectObj.getTasks().isEmpty()) {
                                for (Task taskObj : subProjectObj.getTasks()) {
                                    if (taskObj.equals(task)) {
                                        taskidInSubProject = true;
                                    }
                                }
                                if (!taskidInSubProject) {
                                    subProjectObj.getTasks().add(task);
                                }
                            } else {
                                subProjectObj.getTasks().add(task);
                            }
                        }
                    }
                }
            }

            if (project.getSubProjects() != null) {
                Collections.sort(project.getSubProjects());
            }

            int[] updateCounts = ps.executeBatch();

            // end transaction
            connection.commit();

        } catch (Exception e) {
            System.out.println("Fail to add project to db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
        return project;
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws SQLException{
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            // start transaction
            connection.setAutoCommit(false);

            String SQL = "UPDATE projects set projectname = ?, enddate = ? WHERE projectid = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, newProjectName);
            ps.setString(2, enddate);
            ps.setInt(3, projectid);
            ps.executeUpdate();

            // end transaction
            connection.commit();

        } catch (Exception e) {
            System.out.println("Fail to edit project in db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public void deleteProject(int projectid) throws SQLException{
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);

            // start transaction
            connection.setAutoCommit(false);

            String SQL1 = "DELETE taskrelations FROM taskrelations INNER JOIN tasks ON taskrelations.taskid = tasks.taskid WHERE projectid = ?";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setInt(1, projectid);
            ps1.executeUpdate();

            String SQL2 = "DELETE FROM tasks WHERE projectid = ?";
            PreparedStatement ps2 = connection.prepareStatement(SQL2);
            ps2.setInt(1, projectid);
            ps2.executeUpdate();

            String SQL3 = "DELETE FROM subprojects WHERE projectid = ?";
            PreparedStatement ps3 = connection.prepareStatement(SQL3);
            ps3.setInt(1, projectid);
            ps3.executeUpdate();

            String SQL4 = "DELETE FROM projectrelations WHERE projectid = ?";
            PreparedStatement ps4 = connection.prepareStatement(SQL4);
            ps4.setInt(1, projectid);
            ps4.executeUpdate();

            String SQL5 = "DELETE FROM projects WHERE projectid = ?";
            PreparedStatement ps5 = connection.prepareStatement(SQL5);
            ps5.setInt(1, projectid);
            ps5.executeUpdate();

            // end transaction
            connection.commit();

        } catch (Exception e) {
            System.out.println("Fail to delete project from db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public List<Project> getProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        List<SubProject> subProjects = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            // start transaction
            connection.setAutoCommit(false);

            String SQL = "SELECT * FROM projects";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            // Get data from database.
            while (rs.next()) {

                int projectid = rs.getInt("projectid");
                String projectname = rs.getString("projectname");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String startDateTemp = rs.getString("startdate");
                LocalDate startDate = LocalDate.parse(startDateTemp, formatter);

                String endDateTemp = rs.getString("startdate");
                LocalDate endDate = LocalDate.parse(endDateTemp, formatter);

                Project project = new Project(projectname, startDate, endDate, subProjects);
                project.setProjectid(projectid);

            }
            // end transaction
            connection.commit();
        } catch (Exception e) {
            System.out.println("Fail to get projects from db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
        return projects;
    }


    public Team getTeam(int teamid) throws SQLException {
        List<User> teamUsers = new ArrayList<>();
        Team team = new Team();
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);

            // start transaction
            connection.setAutoCommit(false);

            String SQL = "SELECT * FROM users join teamrelations ON users.userid = teamrelations.userid " +
                    "join teams ON teamrelations.teamid = teams.teamid";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String teamName = "";

            while (rs.next()) {
                String username = rs.getString("username");
                int userid = rs.getInt("userid");
                String name = rs.getString("name");
                int id = rs.getInt("teamid");
                teamName = rs.getString("teamname");

                User user = new User(username, name);
                user.setUserid(userid);

                if (teamid == id) {
                    teamUsers.add(user);
                    team.setTeamName(teamName);
                }
            }

            team.setTeam(teamUsers);

            int[] updateCounts = ps.executeBatch();

            // end transaction
            connection.commit();
        }catch (Exception e) {
            System.out.println("Fail to get user team id from db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
        return team;
    }

    public int getUserTeamId(int userid) throws SQLException{
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);

            // start transaction
            connection.setAutoCommit(false);
            String SQL = "SELECT * FROM teamrelations WHERE userid = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int teamid = rs.getInt("teamid");
                return teamid;
            }

            int[] updateCounts = ps.executeBatch();

            // end transaction
            connection.commit();
        }catch (Exception e) {
            System.out.println("Fail to get user team id from db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
        return 0;
    }

}
