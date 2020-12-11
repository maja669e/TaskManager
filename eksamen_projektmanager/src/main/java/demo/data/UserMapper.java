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
    }

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
    }

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
    }

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
    }
}