package demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // facade to datasource layer
    private DataFacade facade = null;

    public UserService(DataFacade facade) {
        this.facade = facade;
    }

    public Project addProject(int userid) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();
        String projectName = "nyt projekt";
        LocalDate localDate = LocalDate.now();

        Project project = new Project(projectName, localDate, localDate, subProjects);

        facade.addProject(project, userid);

        return project;
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return facade.Login(userName, password);
    }


    public List<Project> getProjects(int userid) throws ProjectManagerException {
        return facade.getProjects(userid);
    }

    public Project getSingleProject(int projectid) throws ProjectManagerException {
        return facade.getSingleProject(projectid);
    }

    public void addSubProject(Project project, List<Task> tasks) throws ProjectManagerException {
        String subProjectName = "nyt delprojekt";
        SubProject subProject = new SubProject(subProjectName, tasks);
        project.getSubProjects().add(subProject);
        facade.addSubProject(project, tasks);
    }

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException {
        return facade.getSubProjects(projectid);
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws ProjectManagerException {
        facade.editProject(projectid, newProjectName, enddate);
    }

    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        facade.changeSubProjectName(subProjectid, newSubProjectName);
    }

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException {
        return facade.getTasks(subprojectid);
    }

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        Task task = new Task(taskName);
        subProject.getTasks().add(task);
        facade.addTask(project, subProject, taskName);
    }

    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        facade.deleteSubproject(subprojectid);
    }

    public void deleteTask(int taskid) throws ProjectManagerException {
        facade.deleteTask(taskid);
    }

    public void deleteProject(int projectid) throws ProjectManagerException {
        facade.deleteProject(projectid);
    }

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        facade.editTask(taskid, taskName, timeEstimate, deadline);
    }

    public void setTaskstatus(int taskid, int taskstatus) throws ProjectManagerException {
        facade.setTaskstatus(taskid, taskstatus);
    }

    public Task getTask(int taskid) throws ProjectManagerException {
        return facade.getTask(taskid);
    }

    public Team getTeam(int teamid) throws ProjectManagerException {
        return facade.getTeam(teamid);
    }

    public int getUserTeamId(int userid) throws ProjectManagerException {
        return facade.getUserTeamId(userid);
    }

    public User getUser(String userName) throws ProjectManagerException {
        return facade.getUser(userName);
    }

    public void addMemberToTask(int taskid, int userid) throws ProjectManagerException {
        facade.addMemberToTask(taskid,userid);
    }

    public List<User> getTaskMembers(int taskid) throws ProjectManagerException{
        return facade.getTaskMembers(taskid);
    }
}
