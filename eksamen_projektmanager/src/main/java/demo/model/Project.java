package demo.model;

import java.util.List;

public class Project {
    private String projectName;
    private int projectid;
    private List<SubProject> subProjects;
    private List<Task> tasks;

    public Project(String projectName, List<SubProject> subProjects) {
        this.projectName = projectName;
        this.subProjects = subProjects;
    }

    public Project(int projectid, String projectName, List<SubProject> subProjects) {
        this.projectid = projectid;
        this.projectName = projectName;
        this.subProjects = subProjects;
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

<<<<<<< HEAD


=======
    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectid=" + projectid +
                ", subProjects=" + subProjects +
                '}';
    }
>>>>>>> 6457e9fcb0e62be30c5455294386bef7fbe5eec9
}
