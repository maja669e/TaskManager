package demo.model;

public interface DataFacade {

    public Project addProject(Project project) throws ProjectManagerException;
    public User Login(String userName, String password) throws ProjectManagerException;
}
