package demo.model;

import java.util.List;

public interface DataFacade {

    public void addProject(Project project, int userid) throws ProjectManagerException;
    public User Login(String userName, String password) throws ProjectManagerException;
    public List<Project> getProjects(int userid) throws ProjectManagerException ;
    public Project getSingleProject(int projectid) throws ProjectManagerException;
    public void addSubProject(Project project, String subProjectName) throws ProjectManagerException;
    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException;
    public void changeProjectName(int projectid, String newProjectName) throws ProjectManagerException;
}
