package demo.service;

import demo.model.*;

import java.util.List;

public class SubProjectService {

    private DataFacade facade = null;

    public SubProjectService(DataFacade facade) {
        this.facade = facade;
    }

    public void addSubProject(Project project) throws ProjectManagerException {
        String subProjectName = "nyt delprojekt";
        SubProject subProject = new SubProject(subProjectName);
        project.getSubProjects().add(subProject);
        facade.addSubProject(project);
    }

    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException {
        facade.changeSubProjectName(subProjectid, newSubProjectName);
    }

    public void deleteSubproject(int subprojectid) throws ProjectManagerException {
        facade.deleteSubproject(subprojectid);
    }

}
