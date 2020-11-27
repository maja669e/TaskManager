package demo.data;

import demo.model.DataFacade;
import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.User;

import java.util.List;

public class DataFacadeImpl implements DataFacade {

    private UserMapper userMapper = new UserMapper();

    public Project addProject(Project project, int userid) throws ProjectManagerException {
        userMapper.addProject(project, userid);
        return project;
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return userMapper.Login(userName, password);
    }

    public List<Project> getProjects(int userid) throws ProjectManagerException {
        return userMapper.getProjects(userid);
    }


}
