package demo.model;

import java.util.List;
import java.util.Objects;

public class SubProject implements Comparable<SubProject>{
    private String subProjectName;
    private int subProjectID;
    private List<Task> tasks;

    public SubProject(String subProjectName) {
        this.subProjectName = subProjectName;
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

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    //Calculate total time for subproject
    public int calSubProjectTotalTime() {
        int totalTime = 0;
        for (int i = 0; i < tasks.size(); i++) {
            totalTime += tasks.get(i).getTimeEstimation();
        }

        return totalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubProject)) return false;
        SubProject that = (SubProject) o;
        return subProjectID == that.subProjectID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subProjectID);
    }

    @Override
    public int compareTo(SubProject o) {
        if(o == null){
            return 1;
        } if(this.subProjectID > o.subProjectID){
            return 1;
        } else if(this.subProjectID < o.subProjectID){
            return -1;
        } else {
            return 0;
        }
    }
}
