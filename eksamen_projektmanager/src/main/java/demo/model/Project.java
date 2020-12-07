package demo.model;

import java.time.LocalDate;
import java.util.List;

public class Project {
    private String projectName;
    private int projectid;
    private LocalDate expStartDate;
    private LocalDate expEndDate;
    private List<SubProject> subProjects;
    private List<Task> tasks;

    public Project(String projectName, LocalDate expStartDate, LocalDate expEndDate, List<SubProject> subProjects) {
        this.projectName = projectName;
        this.expStartDate = expStartDate;
        this.expEndDate = expEndDate;
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

    public LocalDate getExpStartDate() {
        return expStartDate;
    }

    public void setExpStartDate(LocalDate expStartDate) {
        this.expStartDate = expStartDate;
    }

    public LocalDate getExpEndDate() {
        return expEndDate;
    }

    public void setExpEndDate(LocalDate expEndDate) {
        this.expEndDate = expEndDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectid=" + projectid +
                ", subProjects=" + subProjects +
                '}';
    }
}
