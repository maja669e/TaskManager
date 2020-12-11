package demo.service;

import demo.model.*;

import java.util.List;

public class TaskService {
    // facade to datasource layer
    private DataFacade facade = null;

    public TaskService(DataFacade facade) {
        this.facade = facade;
    }

    public void addMemberToTask(int taskid, int userid) throws ProjectManagerException {
        facade.addMemberToTask(taskid,userid);
    }

    public List<User> getTaskMembers(int taskid) throws ProjectManagerException{
        return facade.getTaskMembers(taskid);
    }

    public void deleteMemberFromTask(int taskid, int userid) throws ProjectManagerException {
        facade.deleteMemberFromTask(taskid, userid);
    }

    public List<Task> getTasks(int subprojectid) throws ProjectManagerException {
        return facade.getTasks(subprojectid);
    }

    public void addTask(Project project, SubProject subProject, String taskName) throws ProjectManagerException {
        Task task = new Task(taskName);
        subProject.getTasks().add(task);
        facade.addTask(project, subProject, taskName);
    }
    public void deleteTask(int taskid) throws ProjectManagerException {
        facade.deleteTask(taskid);
    }

    public void editTask(int taskid, String taskName, int timeEstimate, String deadline) throws ProjectManagerException {
        facade.editTask(taskid, taskName, timeEstimate, deadline);
    }

    public void setTaskstatus(int taskid, int taskstatus) throws ProjectManagerException {
        facade.setTaskstatus(taskid, taskstatus);
    }

    public Task getTask(int taskid) throws ProjectManagerException {
        return facade.getTask(taskid);
    }
}
