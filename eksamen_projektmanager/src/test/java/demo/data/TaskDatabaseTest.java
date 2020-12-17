package demo.data;

import demo.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TaskDatabaseTest {

    JdbcTestFixture fixture;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new JdbcTestFixture();
        fixture.setUp();
    }

    @Test
    void editTask() throws SQLException {
        //arrange
        Project project = fixture.getProject(1);
        fixture.addSubProject(project);

        //act
        String expTaskName = "opgave 6";
        int expTimeEstimate = 25;
        String expDeadline = "2021-03-03";
        int expTaskid = 1;

        fixture.editTask(1, "opgave 6", 25, "2021-03-03");

        //assert
        assertEquals(expTaskid, fixture.getProject(1).getSubProjects().get(1).getTasks().get(0).getTaskId());
        assertEquals(expTaskName, fixture.getProject(1).getSubProjects().get(1).getTasks().get(0).getTaskName());
        assertEquals(expDeadline, fixture.getProject(1).getSubProjects().get(1).getTasks().get(0).getDeadLine().toString());
        assertEquals(expTimeEstimate, fixture.getProject(1).getSubProjects().get(1).getTasks().get(0).getTimeEstimation());

    }

    @Test
    void addTask() {
    }

    @Test
    void deleteTask() {
    }
}