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

    public Project getSingleProject(int projectid) throws ProjectManagerException{
        return userMapper.getSingleProject(projectid);
    }

    public void addSubProject(Project project, String subProjectName, List<Task> tasks) throws ProjectManagerException {
        userMapper.addSubProject(project,subProjectName);
    }

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException{
        return userMapper.getSubProjects(projectid);
    }

    public void changeProjectName(int projectid, String newProjectName) throws ProjectManagerException{
        userMapper.changeProjectName(projectid, newProjectName);
    }


    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        userMapper.changeSubProjectName(subProjectid, newSubProjectName);
    }

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException{
        return userMapper.getTasks(subprojectid);
    }

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        userMapper.addTask(project, subProject, taskName);
    }

    public void deleteSubproject(int subprojectid)throws ProjectManagerException {
        userMapper.deleteSubproject(subprojectid);
    }

    public void deleteProject(int projectid)throws ProjectManagerException {
        userMapper.deleteProject(projectid);
    }

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        userMapper.editTask(taskid, taskName, timeEstimate, deadline);
    }
}
