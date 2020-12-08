package demo.data;

import demo.model.*;

import java.util.List;

public class DataFacadeImpl implements DataFacade {

    private UserMapper userMapper = new UserMapper();

    public void addProject(Project project, int userid) throws ProjectManagerException {
        userMapper.addProject(project, userid);
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return userMapper.Login(userName, password);
    }

    public List<Project> getProjects(int userid) throws ProjectManagerException {
        return userMapper.getProjects(userid);
    }

    public Project getSingleProject(int projectid) throws ProjectManagerException {
        return userMapper.getSingleProject(projectid);
    }

    public void addSubProject(Project project, List<Task> tasks) throws ProjectManagerException {
        userMapper.addSubProject(project);
    }

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException {
        return userMapper.getSubProjects(projectid);
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws ProjectManagerException {
        userMapper.editProject(projectid, newProjectName, enddate);
    }


    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        userMapper.changeSubProjectName(subProjectid, newSubProjectName);
    }

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException {
        return userMapper.getTasks(subprojectid);
    }

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        userMapper.addTask(project, subProject, taskName);
    }

    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        userMapper.deleteSubproject(subprojectid);
    }


    public void deleteTask(int taskid) throws ProjectManagerException {
        userMapper.deleteTask(taskid);
    }

    public void deleteProject(int projectid) throws ProjectManagerException {
        userMapper.deleteProject(projectid);
    }

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        userMapper.editTask(taskid, taskName, timeEstimate, deadline);
    }

    public void setTaskstatus(int taskid, int taskstatus) throws ProjectManagerException {
        userMapper.setTaskstatus(taskid, taskstatus);
    }

    public Task getTask(int taskid) throws ProjectManagerException {
        return userMapper.getTask(taskid);
    }

    public List<User> getTeam(int teamid) throws ProjectManagerException {
        return userMapper.getTeam(teamid);
    }

    public int getUserTeamId(int userid) throws ProjectManagerException {
        return userMapper.getUserTeamId(userid);
    }

    public String getTeamName(int teamid) throws ProjectManagerException{
        return userMapper.getTeamName(teamid);
    }
}
