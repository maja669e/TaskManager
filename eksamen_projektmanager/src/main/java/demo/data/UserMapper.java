package demo.data;

import demo.model.*;

import java.awt.print.PrinterJob;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public User Login(String userName, String password) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM users "
                    + "WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int userid = rs.getInt("userid");
                User user = new User(userName, password, userid, name);
                return user;
            } else {
                throw new ProjectManagerException("Kan ikke valider bruger - prøv igen");
            }
        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //UserMapper

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();

            String SQL = " SELECT * FROM subprojects;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            // Get data from database.
            while (rs.next()) {

                int subprojectid = rs.getInt("subprojectid");
                int id = rs.getInt("projectid");
                String subprojectname = rs.getString("subprojectname");

                SubProject subProject = new SubProject(subprojectname, tasks);
                subProject.setSubProjectID(subprojectid);

                if (projectid == id) {
                    subProjects.add(subProject);
                }
            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
        return subProjects;
    } //SubProjectMapper

    public void addSubProject(Project project) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM subprojects";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            int temp = 0;

            while (rs.next()) {
                temp = rs.getInt("subprojectid"); //gets last row subprojectid
            }
            int subprojectid = temp + 1;
            //--------------------------------------------------//

            String SQL2 = "INSERT INTO subprojects (subprojectid, projectid) VALUES (?,?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, subprojectid);
            ps2.setInt(2, project.getProjectid());
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //SubProjectMapper

    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE subprojects set subprojectname = ? WHERE subprojectid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, newSubProjectName);
            ps.setInt(2, subProjectid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //SubProjectMapper

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException {
        List<Task> tasks = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();

            String SQL = "SELECT * FROM tasks";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            // Get data from database.
            while (rs.next()) {

                int id = rs.getInt("subprojectid");

                String temp = rs.getString("deadline");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate deadline = LocalDate.parse(temp, formatter);

                String taskname = rs.getString("taskname");
                int timeEstimation = rs.getInt("timeestimate");
                int taskId = rs.getInt("taskid");
                int taskstatus = rs.getInt("taskstatus");

                Task task = new Task(deadline, timeEstimation, taskname);
                task.setTaskId(taskId);
                task.setTaskStatus(taskstatus);


                if (subprojectid == id) {
                    tasks.add(task);
                }

            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }

        return tasks;
    } //TaskMapper

    public Task getTask(int taskid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = " SELECT * FROM tasks WHERE taskid=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("subprojectid");

                String temp = rs.getString("deadline");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate deadline = LocalDate.parse(temp, formatter);

                String taskname = rs.getString("taskname");
                int timeEstimation = rs.getInt("timeestimate");
                int taskId = rs.getInt("taskid");
                int taskstatus = rs.getInt("taskstatus");

                Task task = new Task(deadline, timeEstimation, taskname);
                task.setTaskId(taskId);
                task.setTaskStatus(taskstatus);

                return task;
            } else {
                throw new ProjectManagerException("Kunne ikke vælge projekt");
            }
        } catch (SQLException | ProjectManagerException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();

            String SQL1 = "DELETE taskrelations FROM taskrelations INNER JOIN tasks ON taskrelations.taskid = tasks.taskid WHERE subprojectid = ?";
            PreparedStatement ps1 = con.prepareStatement(SQL1);
            ps1.setInt(1, subprojectid);
            ps1.executeUpdate();

            String SQL2 = "DELETE FROM tasks WHERE subprojectid = ?";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, subprojectid);
            ps2.executeUpdate();

            String SQL3 = "DELETE FROM subprojects WHERE subprojectid = ?";
            PreparedStatement ps3 = con.prepareStatement(SQL3);
            ps3.setInt(1, subprojectid);
            ps3.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //SubProjectMapper

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE tasks set taskname = ?, timeestimate = ?, deadline= ? WHERE taskid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, taskName);
            ps.setInt(2, timeEstimate);
            ps.setString(3, deadline);
            ps.setInt(4, taskid);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public void setTaskstatus(int taskid, int taskstatus) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE tasks set taskstatus = ? WHERE taskid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskstatus);
            ps.setInt(2, taskid);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM tasks";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            int temp = 0;

            while (rs.next()) {
                temp = rs.getInt("taskid"); //gets last row subprojectid
            }
            int taskid = temp + 1;
            //--------------------------------------------------//
            String SQL2 = "INSERT INTO tasks (taskid, projectid, subprojectid, taskname, taskstatus) VALUES (?,?,?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, taskid);
            ps2.setInt(2, project.getProjectid());
            ps2.setInt(3, subProject.getSubProjectID());
            ps2.setString(4, taskName);
            ps2.setString(5, String.valueOf(2));
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public void deleteTask(int taskid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();

            String SQL = "DELETE FROM taskrelations WHERE taskid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskid);
            ps.executeUpdate();

            String SQL2 = "DELETE FROM tasks WHERE taskid = ?";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, taskid);
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public int getUserTeamId(int userid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM teamrelations WHERE userid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int teamid = rs.getInt("teamid");
                return teamid;

            } else {
                throw new ProjectManagerException("Kunne ikke finde bruger team id");
            }
        } catch (SQLException | ProjectManagerException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //UserMapper

    public Team getTeam(int teamid) throws ProjectManagerException {
        List<User> teamUsers = new ArrayList<>();
        Team team = new Team();
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM users join teamrelations ON users.userid = teamrelations.userid " +
                    "join teams ON teamrelations.teamid = teams.teamid";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String teamName ="";

            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                int id = rs.getInt("teamid");
                teamName = rs.getString("teamname");

                User user = new User(username, name);

                if(teamid == id){
                    teamUsers.add(user);
                    team.setTeamName(teamName);
                }
            }
            team.setTeam(teamUsers);
        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
        return team;
    } //UserMapper

    public User getUser(String userName) throws ProjectManagerException {
        try {
        Connection con = DBManager.getConnection();
        String SQL = " SELECT * FROM users WHERE username=?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int userid = rs.getInt("userid");
            String name = rs.getString("name");

            User user = new User(userName, name);
            user.setUserid(userid);

            return user;
        } else {
            throw new ProjectManagerException("kunne ikke finde bruger");
        }
    } catch (SQLException | ProjectManagerException ex) {
        throw new ProjectManagerException(ex.getMessage());
    }
    } //UserMapper

    public void addMemberToTask(int taskid, int userid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM taskrelations";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            int temp = 0;

            while (rs.next()) {
                temp = rs.getInt("taskrelationid");
            }

            int taskrelationid = temp + 1;
            //--------------------------------------------------//

            String SQL2 = "INSERT INTO taskrelations (taskrelationid, taskid, userid) VALUES (?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, taskrelationid);
            ps2.setInt(2, taskid);
            ps2.setInt(3, userid);
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper

    public List<User> getTaskMembers(int taskid) throws ProjectManagerException{
        List<User> taskMembers = new ArrayList<>();
            try {
            Connection con = DBManager.getConnection();

            String SQL = "SELECT * FROM taskrelations INNER JOIN users ON taskrelations.userid = users.userid WHERE taskid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, taskid);
            ResultSet rs = ps.executeQuery();
            // Get data from database.
            while (rs.next()) {

                int userid = rs.getInt("userid");
                String username = rs.getString("username");
                String name = rs.getString("name");

                User user = new User(username,name);
                user.setUserid(userid);

                taskMembers.add(user);
            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }

        return taskMembers;
    } //TaskMapper

    public void deleteMemberFromTask(int taskid, int userid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();

            String SQL1 = "DELETE FROM taskrelations WHERE taskid = ? AND userid = ?";
            PreparedStatement ps1 = con.prepareStatement(SQL1);
            ps1.setInt(1, taskid);
            ps1.setInt(2, userid);
            ps1.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    } //TaskMapper
}