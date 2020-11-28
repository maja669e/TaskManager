package demo.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testIfProjectIsinitialized() {
        //arrange
        List<SubProject> subProjects = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        String projectName = "nyt projekt";

        //act
        Project project = new Project(projectName, subProjects, tasks);
        String exp = "nyt projekt";
        String act = project.getProjectName();

        //assert
        assertEquals(exp, act);
    }
}