package demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private LocalDate expStartDate = LocalDate.of(2020, 02, 02);
    private LocalDate expEndDate = LocalDate.of(2020, 3, 12);

    @Test
    void calWorkHoursPerDay() {
        //Arrange
        double totalProjectHours = 53;

        //Act
        int dif = (int) ChronoUnit.DAYS.between(expStartDate, expEndDate);
        dif = Math.abs(dif);

        double workHoursPerDay;

        if (dif == 0) {
            workHoursPerDay = totalProjectHours;
        } else {
            workHoursPerDay = totalProjectHours / dif;
        }
        double expWorkHoursPerDay = 1.358974358974359;

        //Assert
        assertEquals(expWorkHoursPerDay, workHoursPerDay);
    }

    @Test
    void convertProjectHoursPerDayToAbs() {
        //Arrange
        int hours = (int) 1.358974358974359;

        //Act
        Math.abs(hours);

        int expHours = 1;
        //Assert
        assertEquals(expHours, hours);
    }

    @Test
    void convertProjectHoursToMinutesPerDay() {
        //convert the remaining time to minutes
        //Arrange
        double res = 1.358974358974359 % 1;
        //Act
        int minutes = (int) (res * 60);
        int expMinutes = 21;
        //Assert
        assertEquals(expMinutes, minutes);
    }

    @Test
    void calProjectTotalTime() {

        //Arrange
        List<SubProject> subProjects = new ArrayList<>();
        SubProject subProject;

        Task task = new Task(LocalDate.of(2020, 04, 05), 4, "Test");
        Task task2 = new Task(LocalDate.of(2020, 04, 05), 5, "Test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);


        //Act
        int totalTime = 0;
        for (int i = 0; i < tasks.size(); i++) {
            totalTime += tasks.get(i).getTimeEstimation();
            for (int j = 0; j < subProjects.size(); j++) {
                subProject = subProjects.get(j);
                totalTime += subProject.calSubProjectTotalTime();
            }
        }

        int expTotalTime = 9;

        //Assert
        assertEquals(expTotalTime, totalTime);
    }


    @Test
    void calUserWorkHoursOnProject() {
        //Arrange

        int userid = 0;

        List<SubProject> subProjects = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        Task task = new Task(LocalDate.of(2020, 06, 06), 7, "Test");
        tasks.add(task);

        User user = new User("test", "1", 1, "Test");
        user.setUserid(userid);
        users.add(user);
        task.setTaskMembers(users);

        SubProject subProject = new SubProject("Test");
        subProjects.add(subProject);
        subProject.setTasks(tasks);


        int sum = 0;

        //Act
        for (int i = 0; i < subProjects.size(); i++) {
            for (int j = 0; j < subProjects.get(i).getTasks().size(); j++) {
                for (int k = 0; k < subProjects.get(i).getTasks().get(j).getTaskMembers().size(); k++) {
                    if (subProjects.get(i).getTasks().get(j).getTaskMembers().get(k).getUserid() == userid) {
                        sum += subProjects.get(i).getTasks().get(j).getTimeEstimation();
                    }
                }
            }
        }

        int expSum = 7;
        //Assert
        assertEquals(expSum, sum);
    }
}