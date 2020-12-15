package demo.data;

import demo.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



/**
 * @author Nicolai Okkels
 */
public class ProjectDatabaseTest {

    JdbcTestFixture fixture;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new JdbcTestFixture();
        fixture.setUp();
    }

    @Test
    void addProject() throws SQLException {
        //arrange
        fixture.addProject();

        //act
        int expProjectid = 3;
        Project project = fixture.getProject(3);

        //assert
        assertEquals(expProjectid, project.getProjectid());
    }

    @Test
    void editProject() throws SQLException {
        //arrange
        fixture.addProject();

        //act
        String expName = "nyt test projekt";
        String expDate = "2030-01-01";
        fixture.editProject(3, expName, expDate);

        Project project = fixture.getProject(3);

        //assert
        assertEquals(expName, project.getProjectName());
        assertEquals(expDate, project.getExpEndDate().toString());
    }

    @Test
    void deleteProject() throws SQLException {
        //arrange
        fixture.addProject();
        Project project = fixture.getProject(3);
        List<Project> projects = fixture.getProjects();

        //act
        fixture.deleteProject(project.getProjectid());
        boolean inProjects = false;

        for (Project projectObj : projects) {
            if (projectObj.equals(project)) {
                inProjects = true;
            }
        }

        //assert
        assertFalse(inProjects);
    }
}
