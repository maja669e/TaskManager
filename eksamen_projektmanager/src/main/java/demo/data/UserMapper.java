package demo.data;

import demo.model.*;

import java.awt.print.PrinterJob;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public void addProject(Project project, int userid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();

            ///////////////////////////////////////

            String SQL = "SELECT * FROM projects";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();
            int projectid = 0;

            while (rs.next()) {
                projectid = rs.getInt("projectid"); //gets last row projectid
            }

            ///////////////////////////////////////

            String SQL2 = "INSERT INTO projects (projectid) VALUES (?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            project.setProjectid(projectid + 1);
            ps2.setInt(1, project.getProjectid());
            ps2.executeUpdate();
            ///////////////////////////////////////

            String SQL4 = "SELECT * FROM projectrelations";
            PreparedStatement ps4 = con.prepareStatement(SQL4);

            ResultSet rs3 = ps4.executeQuery();
            int projectrelationid = 0;

            while (rs3.next()) {
                projectrelationid = rs3.getInt("projectrelationid"); //gets last row projectrelationid
            }
            ///////////////////////////////////////

            String SQL5 = "INSERT INTO projectrelations (projectrelationid, userid, projectid) VALUES (?,?,?)";
            PreparedStatement ps5 = con.prepareStatement(SQL5);
            ps5.setInt(1, projectrelationid + 1);
            ps5.setInt(2, userid);
            ps5.setInt(3, project.getProjectid());
            ps5.executeUpdate();


        } catch (SQLException ex) {
            throw new ProjectManagerException("Kunne ikke tilføje projekt");
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

    public List<Project> getProjects(int userid) throws ProjectManagerException {
        List<Project> projects = new ArrayList<>();
        List<SubProject> subProjects = new ArrayList<>();

        try {
            Connection con = DBManager.getConnection();

            String SQL = "SELECT * FROM projectrelations LEFT JOIN projects ON projectrelations.projectid = projects.projectid";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            // Get data from database.
            while (rs.next()) {

                int projectid = rs.getInt("projectid");
                int id = rs.getInt("userid");
                String projectname = rs.getString("projectname");

                Project project = new Project(projectid, projectname, subProjects);

                if (userid == id) {
                    projects.add(project);
                }
            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
        return projects;
    }

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
    }

    public Project getSingleProject(int projectid) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();

        try {
            Connection con = DBManager.getConnection();
            String SQL = " SELECT * FROM projects WHERE projectid=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String projectName = rs.getString("projectname");
                Project project = new Project(projectid, projectName, subProjects);

                return project;
            } else {
                throw new ProjectManagerException("Kunne ikke vælge projekt");
            }
        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

    public void addSubProject(Project project, String subProjectName) throws ProjectManagerException {
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

            String SQL2 = "INSERT INTO subprojects (subprojectid, projectid, subprojectname) VALUES (?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, subprojectid);
            ps2.setInt(2, project.getProjectid());
            ps2.setString(3, subProjectName);
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

    public void changeProjectName(int projectid, String newProjectName) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE projects set projectname = ? WHERE projectid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, newProjectName);
            ps.setInt(2, projectid);
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE subProjects set subprojectname = ? WHERE subprojectid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, newSubProjectName);
            ps.setInt(2, subProjectid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

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
                int taskid = rs.getInt("taskid");

                Task task = new Task(deadline, timeEstimation, taskname);
                task.setTaskId(taskid);

                if (subprojectid == id) {
                    tasks.add(task);
                }

            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }

        return tasks;
    }

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


    public void deleteProject(int projectid) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();

            String SQL1 = "DELETE taskrelations FROM taskrelations INNER JOIN tasks ON taskrelations.taskid = tasks.taskid WHERE projectid = ?";
            PreparedStatement ps1 = con.prepareStatement(SQL1);
            ps1.setInt(1, projectid);
            ps1.executeUpdate();

            String SQL2 = "DELETE FROM tasks WHERE projectid = ?";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, projectid);
            ps2.executeUpdate();

            String SQL3 = "DELETE FROM subprojects WHERE projectid = ?";
            PreparedStatement ps3 = con.prepareStatement(SQL3);
            ps3.setInt(1, projectid);
            ps3.executeUpdate();

            String SQL4 = "DELETE FROM projectrelations WHERE projectid = ?";
            PreparedStatement ps4 = con.prepareStatement(SQL4);
            ps4.setInt(1, projectid);
            ps4.executeUpdate();

            String SQL5 = "DELETE FROM projects WHERE projectid = ?";
            PreparedStatement ps5 = con.prepareStatement(SQL5);
            ps5.setInt(1, projectid);
            ps5.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

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
    }


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
            String SQL2 = "INSERT INTO tasks (taskid, projectid, subprojectid, taskname) VALUES (?,?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            ps2.setInt(1, taskid);
            ps2.setInt(2, project.getProjectid());
            ps2.setInt(3, subProject.getSubProjectID());
            ps2.setString(4, taskName);
            ps2.executeUpdate();

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }
}