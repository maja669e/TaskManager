package demo.data;

import demo.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Phuc Nguyen
 */
class SubProjectDatabaseTest {

    JdbcTestFixture fixture;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new JdbcTestFixture();
        fixture.setUp();
    }


    @Test
    void addSubProject() throws SQLException {
        //arrange
        Project project = fixture.getProject(1);
        fixture.addSubProject(project);

        //act
        int expSubProjectid = 4;

        //assert
        assertEquals(expSubProjectid, fixture.getProject(1).getSubProjects().get(3).getSubProjectID());
    }

    @Test
    void changeSubProjectName() throws SQLException {
        //arrange
        Project project = fixture.getProject(1);
        fixture.addSubProject(project);

        //act
        String expName = "subprojekt 2";
        fixture.changeSubProjectName(1, "subprojekt 2");

        //assert
        assertEquals(expName, fixture.getProject(1).getSubProjects().get(0).getSubProjectName());

    }
}