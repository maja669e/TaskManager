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

public class TaskMapper {

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
    }

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
    }

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
    }

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
    }

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
    }

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
    }

}
