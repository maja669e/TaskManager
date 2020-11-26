package demo.data;

import demo.model.Project;
import demo.model.ProjectManagerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}