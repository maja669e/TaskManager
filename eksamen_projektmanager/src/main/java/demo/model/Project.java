package demo.model;

import java.util.List;

public class Project {
    private String projectName;
    private List<SubProject> subProjects;
    private List<Task> tasks;

    public Project(String projectName, List<SubProject> subProjects, List<Task> tasks) {
        this.projectName = projectName;
        this.subProjects = subProjects;
        this.tasks = tasks;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
}
