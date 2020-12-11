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


}