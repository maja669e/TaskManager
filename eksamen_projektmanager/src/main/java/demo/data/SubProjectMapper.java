package demo.data;

import demo.model.Project;
import demo.model.ProjectManagerException;

import java.sql.*;

public class SubProjectMapper {

    /**
     * @author Phuc Nguyen
     */
    public void addSubProject(Project project) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "INSERT INTO subprojects (projectid) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, project.getProjectid());
            ps.executeUpdate();

            ResultSet subprojectids = ps.getGeneratedKeys();
            subprojectids.next();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

    /**
     * @author Phuc Nguyen
     */
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
    }

    /**
     * @author Phuc Nguyen
     */
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
    }

}
