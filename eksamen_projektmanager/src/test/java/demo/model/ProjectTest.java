package demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Maja Bijedic
 */
class ProjectTest {

    private LocalDate expStartDate = LocalDate.of(2020, 02, 02);
    private LocalDate expEndDate = LocalDate.of(2020, 02, 12);

    @Test
    void calWorkHoursPerDay() {
        //Arrange

        SubProject subProject = new SubProject("test");

        Task task = new Task(LocalDate.of(2020, 04, 05), 4, "Test");
        Task task2 = new Task(LocalDate.of(2020, 04, 05), 8, "Test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        subProject.setTasks(tasks);
        List<SubProject> subProjects = new ArrayList<>();
        Project project = new Project();
        project.setSubProjects(subProjects);
        project.getSubProjects().add(subProject);

        project.setExpEndDate(expEndDate);
        project.setExpStartDate(expStartDate);
        //Act
        double actSum = project.calWorkHoursPerDay();
        double expWorkHoursPerDay = 1.2;
        //Assert
        assertEquals(expWorkHoursPerDay, actSum);
    }


    @Test
    void convertProjectHoursPerDayToAbs() {
        //Arrange

        SubProject subProject = new SubProject("test");

        Task task = new Task(LocalDate.of(2020, 04, 05), 4, "Test");
        Task task2 = new Task(LocalDate.of(2020, 04, 05), 8, "Test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        subProject.setTasks(tasks);
        List<SubProject> subProjects = new ArrayList<>();
        Project project = new Project();
        project.setSubProjects(subProjects);
        project.getSubProjects().add(subProject);

        project.setExpEndDate(expEndDate);
        project.setExpStartDate(expStartDate);

        //act
        int actSum = project.convertProjectHoursPerDayToAbs();
        int expSum = 1;
        //Assert
        assertEquals(expSum, actSum);
    }

    @Test
    void convertProjectHoursToMinutesPerDay() {
        //convert the remaining time to minutes
        //Arrange
        SubProject subProject = new SubProject("test");

        Task task = new Task(LocalDate.of(2020, 04, 05), 4, "Test");
        Task task2 = new Task(LocalDate.of(2020, 04, 05), 8, "Test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        subProject.setTasks(tasks);
        List<SubProject> subProjects = new ArrayList<>();
        Project project = new Project();
        project.setSubProjects(subProjects);
        project.getSubProjects().add(subProject);

        project.setExpEndDate(expEndDate);
        project.setExpStartDate(expStartDate);
        //Act
        int actSum = project.convertProjectHoursToMinutesPerDay();
        int expSum = 11;
        //Assert
        assertEquals(expSum, actSum);
    }

    @Test
    void calProjectTotalTime() {

        //Arrange
        SubProject subProject = new SubProject("test");

        Task task = new Task(LocalDate.of(2020, 04, 05), 4, "Test");
        Task task2 = new Task(LocalDate.of(2020, 04, 05), 5, "Test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        subProject.setTasks(tasks);
        List<SubProject> subProjects = new ArrayList<>();
        Project project = new Project();
        project.setSubProjects(subProjects);
        project.getSubProjects().add(subProject);

        int actTotalTime = project.calProjectTotalTime();
        int expTotalTime = 9;

        //Assert
        assertEquals(expTotalTime, actTotalTime);
    }


    @Test
    void calUserWorkHoursOnProject() {
        //Arrange

        List<SubProject> subProjects = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        Task task = new Task(LocalDate.of(2020, 06, 06), 7, "Test");
        tasks.add(task);

        User user = new User("test", "1", 1, "Test");
        users.add(user);
        task.setTaskMembers(users);

        SubProject subProject = new SubProject("Test");
        subProjects.add(subProject);
        subProject.setTasks(tasks);

        Project project = new Project();
        project.setSubProjects(subProjects);


        //Act
        int actSum = project.calUserWorkHoursOnProject(1);
        int expSum = 7;
        //Assert
        assertEquals(expSum, actSum);
    }
}