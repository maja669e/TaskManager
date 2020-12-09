package demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private LocalDate deadLine;
    private int timeEstimation;
    private String taskName;
    private int taskId;
    private int taskStatus = 2; //Default set to task not started
    private List<User> taskMembers = new ArrayList<>();

    public Task(LocalDate deadLine, int timeEstimation, String taskName) {
        this.deadLine = deadLine;
        this.timeEstimation = timeEstimation;
        this.taskName = taskName;
    }
    public Task(String taskName) {
        this.taskName = taskName;
    }

    public int getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(int timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<User> getTaskMembers() {
        return taskMembers;
    }

    public void setTaskMembers(List<User> taskMembers) {
        this.taskMembers = taskMembers;
    }

    @Override
    public String toString() {
        return "Task{" +
                "deadLine=" + deadLine +
                ", timeEstimation=" + timeEstimation +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                ", taskMembers=" + taskMembers +
                '}';
    }
}
