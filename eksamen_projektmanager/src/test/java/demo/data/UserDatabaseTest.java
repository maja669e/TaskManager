package demo.data;

import demo.model.Team;
import demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDatabaseTest {

    JdbcTestFixture fixture;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new JdbcTestFixture();
        fixture.setUp();
    }

    @Test
    void getTeam() throws SQLException {
        //arrange
        Team team = fixture.getTeam(1);
        //act
        boolean userOneInTeam = false;

        for (User user : team.getTeam()) {
            if(user.getUserid() == 1){
                userOneInTeam = true;
            }
        }
        //assert
        assertTrue(userOneInTeam);
    }

    @Test
    void getUserTeamId() throws SQLException {
        //arrange
        int actTeamid = fixture.getUserTeamId(3);
        //act
        int expTeamid = 2;
        //assert
        assertEquals(expTeamid, actTeamid);
    }
}
