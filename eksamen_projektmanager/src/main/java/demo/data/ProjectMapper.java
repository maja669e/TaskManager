package demo.data;

import demo.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

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

            String SQL2 = "INSERT INTO projects (projectid, startdate, enddate) VALUES (?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(SQL2);
            project.setProjectid(projectid + 1);
            ps2.setInt(1, project.getProjectid());
            ps2.setString(2, String.valueOf(LocalDate.now()));
            ps2.setString(3, String.valueOf(LocalDate.now()));
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String startDateTemp = rs.getString("startdate");
                LocalDate startDate = LocalDate.parse(startDateTemp, formatter);

                String endDateTemp = rs.getString("startdate");
                LocalDate endDate = LocalDate.parse(endDateTemp, formatter);

                Project project = new Project(projectname, startDate, endDate, subProjects);
                project.setProjectid(projectid);

                if (userid == id) {
                    projects.add(project);
                }
            }

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
        return projects;
    }

    public Project getSingleProject(int projectid) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<User> taskMembers = new ArrayList<>();
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * FROM projects join subprojects ON projects.projectid = subprojects.projectid " +
            "join tasks ON subprojects.subprojectid = tasks.subprojectid " +
            "left join taskrelations ON tasks.taskid = taskrelations.taskid " +
            "left join users ON taskrelations.userid = users.userid where projects.projectid = ? ";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectid);
            ResultSet rs = ps.executeQuery();

            Project project = new Project();

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

                //Subproject
                int subprojectid = rs.getInt("subprojectid");
                String subprojectName = rs.getString("subprojectname");

                SubProject subProject = new SubProject(subprojectName);

                if(!subProjects.contains(subProject)){
                    subProject.setSubProjectID(subprojectid);
                    subProjects.add(subProject);
                }

                //Task
                String temp = rs.getString("deadline");
                LocalDate deadline = LocalDate.parse(temp, formatter);

                int taskid = rs.getInt("taskid");
                String taskname = rs.getString("taskname");
                int timeEstimation = rs.getInt("timeestimate");
                int taskstatus = rs.getInt("taskstatus");

                //Task User
                int userid = rs.getInt("userid");
                String username = rs.getString("username");
                String name = rs.getString("name");

                User user = new User(username,name);
                user.setUserid(userid);

                taskMembers.add(user);

                Task task = new Task(deadline, timeEstimation, taskname);
                task.setTaskId(taskid);
                task.setTaskStatus(taskstatus);

                if(!tasks.contains(task)){
                    tasks.add(task);
                }

            }
            return project;

        } catch (SQLException ex) {
            throw new ProjectManagerException(ex.getMessage());
        }
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws ProjectManagerException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "UPDATE projects set projectname = ?, enddate = ? WHERE projectid = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, newProjectName);
            ps.setString(2, enddate);
            ps.setInt(3, projectid);
            ps.executeUpdate();

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
}
