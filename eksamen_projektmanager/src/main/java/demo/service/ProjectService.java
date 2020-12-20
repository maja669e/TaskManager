package demo.service;

import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.SubProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    // facade to datasource layer
    private DataFacade facade = null;

    public ProjectService(DataFacade facade) {
        this.facade = facade;
    }

    public List<Project> getProjects(int userid) throws ProjectManagerException {
        return facade.getProjects(userid);
    }

    public Project getProject(int projectid) throws ProjectManagerException {
        return facade.getProject(projectid);
    }

    public void editProject(int projectid, String newProjectName, String enddate) throws ProjectManagerException {
        facade.editProject(projectid, newProjectName, enddate);
    }

    public void deleteProject(int projectid) throws ProjectManagerException {
        facade.deleteProject(projectid);
    }

    public Project addProject(int userid) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();
        String projectName = "nyt projekt";
        LocalDate localDate = LocalDate.now();

        Project project = new Project(projectName, localDate, localDate, subProjects);

        facade.addProject(project, userid);

        return project;
    }
}
