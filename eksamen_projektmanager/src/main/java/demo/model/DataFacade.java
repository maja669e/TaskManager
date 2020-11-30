package demo.model;

import java.util.List;

public interface DataFacade {

    public Project addProject(Project project, int userid) throws ProjectManagerException;
    public User Login(String userName, String password) throws ProjectManagerException;
    public List<Project> getProjects(int userid) throws ProjectManagerException ;
    public Project getSingleProject(int projectid) throws ProjectManagerException;
}
