package demo.model;

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
        List<Task> tasks = new ArrayList<>();
        String projectName = "nyt projekt";

        Project project = new Project(projectName, subProjects, tasks);

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
}
