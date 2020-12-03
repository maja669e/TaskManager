package demo.model;

import java.time.LocalDate;

public class Task {
    private LocalDate deadLine;
    private int timeEstimation;
    private String taskName;

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

    @Override
    public String toString() {
        return "Task{" +
                "deadLine=" + deadLine +
                ", timeEstimation=" + timeEstimation +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
