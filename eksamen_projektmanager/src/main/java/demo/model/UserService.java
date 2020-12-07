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

        Project project = new Project(projectName, null, null, subProjects);

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

    public void addSubProject(Project project, String subProjectName, List<Task> tasks) throws ProjectManagerException {
        SubProject subProject = new SubProject(subProjectName, tasks);
        project.getSubProjects().add(subProject);
        facade.addSubProject(project, subProjectName, tasks);
    }

    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException {
        return facade.getSubProjects(projectid);
    }

    public void changeProjectName(int projectid, String newProjectName) throws ProjectManagerException {
        facade.changeProjectName(projectid, newProjectName);
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

}
