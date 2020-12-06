package demo.model;

import java.util.List;

public interface DataFacade {

    public void addProject(Project project, int userid) throws ProjectManagerException;
    public User Login(String userName, String password) throws ProjectManagerException;
    public List<Project> getProjects(int userid) throws ProjectManagerException ;
    public Project getSingleProject(int projectid) throws ProjectManagerException;
    public void addSubProject(Project project, String subProjectName, List<Task> tasks) throws ProjectManagerException;
    public List<SubProject> getSubProjects(int projectid) throws ProjectManagerException;
    public void changeProjectName(int projectid, String newProjectName) throws ProjectManagerException;
    public void changeSubProjectName(int subProjectid, String newSubProjectName) throws ProjectManagerException;
    public List<Task> getTasks(int subprojectid) throws ProjectManagerException;
    public void deleteSubproject(int subprojectid)throws ProjectManagerException;
    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException;
    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException;
}
