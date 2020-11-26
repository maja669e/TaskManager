package demo.data;

import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public void addProject(Project project) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO projects(name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, project.getProjectName());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
            //throw new ProjectManagerException("Kunne ikke tilføje projekt");
        }

    }

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
}