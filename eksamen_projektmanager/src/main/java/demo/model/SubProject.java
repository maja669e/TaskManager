package demo.model;

import java.util.List;

public class SubProject {
    private String subProjectName;
    private int subProjectID;
    private List<Task> tasks;

    public SubProject(String subProjectName, List<Task> tasks) {
        this.subProjectName = subProjectName;
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public SubProject(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    //TODO: ved ikke om denne metode er det rigtige
    public int calSubProjectTotalTime() {
        int totalTime = 0;
        for (int i = 0; i < tasks.size(); i++) {
            totalTime += tasks.get(i).getTimeEstimation();
        }

        return totalTime;
    }

    @Override
    public String toString() {
        return "SubProject{" +
                "subProjectName='" + subProjectName + '\'' +
                ", subProjectID=" + subProjectID +
                ", tasks=" + tasks +
                '}';
    }
}
