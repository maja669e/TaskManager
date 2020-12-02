package demo.model;

import java.util.List;

public class Project {
    private String projectName;
    private int projectid;
    private List<SubProject> subProjects;
    private List<Task> tasks;

    public Project(String projectName, List<SubProject> subProjects, List<Task> tasks) {
        this.projectName = projectName;
        this.subProjects = subProjects;
        this.tasks = tasks;
    }

    public Project(int projectid, String projectName, List<SubProject> subProjects, List<Task> tasks) {
        this.projectid = projectid;
        this.projectName = projectName;
        this.subProjects = subProjects;
        this.tasks = tasks;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
