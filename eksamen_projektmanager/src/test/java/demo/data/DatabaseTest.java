package demo.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;



/**
 * @author Nicolai Okkels
 */
public class DatabaseTest {

    JdbcTestFixture fixture;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new JdbcTestFixture();
        fixture.setUp();
    }


    @Test
    public void InsertData() {
        // invoke backend call to interact with database
        assertTrue(true);
    }

}