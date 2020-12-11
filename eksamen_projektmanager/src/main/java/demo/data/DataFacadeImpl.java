package demo.data;

import demo.model.*;

import java.util.List;

public class DataFacadeImpl implements DataFacade {

    private UserMapper userMapper = new UserMapper();
    private SubProjectMapper subProjectMapper = new SubProjectMapper();
    private ProjectMapper projectMapper = new ProjectMapper();
    private TaskMapper taskMapper = new TaskMapper();


    public void addProject(Project project, int userid) throws ProjectManagerException {
        projectMapper.addProject(project, userid);
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return userMapper.Login(userName, password);
    }

    public List<Project> getProjects(int userid) throws ProjectManagerException {
        return projectMapper.getProjects(userid);
    }

    public Project getSingleProject(int projectid) throws ProjectManagerException {
        return projectMapper.getSingleProject(projectid);
    }

    public void addSubProject(Project project, List<Task> tasks) throws ProjectManagerException {
        subProjectMapper.addSubProject(project);
    }

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException {
        return subProjectMapper.getSubProjects(projectid);
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws ProjectManagerException {
        projectMapper.editProject(projectid, newProjectName, enddate);
    }


    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        subProjectMapper.changeSubProjectName(subProjectid, newSubProjectName);
    }

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException {
        return taskMapper.getTasks(subprojectid);
    }

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        taskMapper.addTask(project, subProject, taskName);
    }

    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        subProjectMapper.deleteSubproject(subprojectid);
    }


    public void deleteTask(int taskid) throws ProjectManagerException {
        taskMapper.deleteTask(taskid);
    }

    public void deleteProject(int projectid) throws ProjectManagerException {
        projectMapper.deleteProject(projectid);
    }

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        taskMapper.editTask(taskid, taskName, timeEstimate, deadline);
    }

    public void setTaskstatus(int taskid, int taskstatus) throws ProjectManagerException {
        taskMapper.setTaskstatus(taskid, taskstatus);
    }

    public Task getTask(int taskid) throws ProjectManagerException {
        return taskMapper.getTask(taskid);
    }

    public Team getTeam(int teamid) throws ProjectManagerException {
        return userMapper.getTeam(teamid);
    }

    public int getUserTeamId(int userid) throws ProjectManagerException {
        return userMapper.getUserTeamId(userid);
    }
    public User getUser(String userName) throws ProjectManagerException {
        return userMapper.getUser(userName);
    }

    public void addMemberToTask(int taskid, int userid) throws ProjectManagerException {
        taskMapper.addMemberToTask(taskid, userid);
    }

    public List<User> getTaskMembers(int taskid) throws ProjectManagerException{
        return taskMapper.getTaskMembers(taskid);
    }

    public void deleteMemberFromTask(int taskid, int userid) throws ProjectManagerException {
        taskMapper.deleteMemberFromTask(taskid, userid);
    }
}
