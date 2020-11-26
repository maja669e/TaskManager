package demo.model;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    // facade to datasource layer
    private DataFacade facade = null;

    public UserService(DataFacade facade) {
        this.facade = facade;
    }

    public Project addProject(Project project) throws ProjectManagerException {
        List<SubProject> subProjects = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        project = new Project(projectName, subProjects, tasks);

        facade.addProject(project);

        return project;
    }
}
