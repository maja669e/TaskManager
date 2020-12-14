package demo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Task {
    private LocalDate deadLine;
    private int timeEstimation;
    private String taskName;
    private int taskId;
    private int taskStatus = 2; //Default set to task not started, 1 is started and 0 is finished
    private List<User> taskMembers;

    public Task(LocalDate deadLine, int timeEstimation, String taskName) {
        this.deadLine = deadLine;
        this.timeEstimation = timeEstimation;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

}
