package demo.data;

import demo.model.DataFacade;
import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.User;

public class DataFacadeImpl implements DataFacade {

    private UserMapper userMapper = new UserMapper();

    public Project addProject(Project project) throws ProjectManagerException {
        userMapper.addProject(project);
        return project;
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return userMapper.Login(userName, password);
    }

}
