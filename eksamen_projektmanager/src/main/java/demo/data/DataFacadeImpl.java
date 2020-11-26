package demo.data;

import demo.model.DataFacade;
import demo.model.Project;
import demo.model.ProjectManagerException;

public class DataFacadeImpl implements DataFacade {

    private UserMapper userMapper = new UserMapper();

    public Project addProject(Project project) throws ProjectManagerException {
        userMapper.addProject(project);
        return project;
    }
}
