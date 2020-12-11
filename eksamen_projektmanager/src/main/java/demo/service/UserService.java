package demo.service;

import demo.model.*;

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



    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        facade.deleteSubproject(subprojectid);
    }


    public void deleteProject(int projectid) throws ProjectManagerException {
        facade.deleteProject(projectid);
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


}
