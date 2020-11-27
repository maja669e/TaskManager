package demo.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTestFixture {

    static final String USER = "root";
    static final String PWD = "root";
    static final String URL = "jdbc:mysql://localhost/projektellotest?serverTimezone=UTC";
    Connection connection;

    public void setUp() throws SQLException{
        try
        {
            connection = DriverManager.getConnection(URL, USER, PWD);
            Statement st= connection.createStatement();
            // start transaction
            connection.setAutoCommit(false);

            // create

            st.addBatch("DROP TABLE if exists projects");
            st.addBatch("create table projects(" +
                            "   projectid       integer not null primary key," +
                            "   projectname     varchar(100) null DEFAULT 'nyt projekt')"
                            );

            st.addBatch("DROP TABLE if exists users");
            st.addBatch("create table users(" +
                    "   userid      integer not null primary key," +
                    "   username    varchar(100) not null,"+
                    "   password    varchar(100) not null,"+
                    "   name        varchar(100) not null)" );

            st.addBatch("DROP TABLE if exists projectrelations");
            st.addBatch("create table projectrelations(" +
                    "   projectrelationid   integer not null primary key,"+
                    "   userid              integer not null," +
                    "   projectid           integer not null");


            // insert
            st.addBatch("INSERT INTO users VALUES (1,'test1', '1', 'Nicolai')" );
            st.addBatch("INSERT INTO users VALUES (2,'test2', '1', 'Phuc')" );
            st.addBatch("INSERT INTO users VALUES (3,'test3', '1', 'Maja')" );
            st.addBatch("INSERT INTO users VALUES (4,'test4', '1', 'Bo')" );
            st.addBatch("INSERT INTO users VALUES (5,'test5', '1', 'Hans')" );

            st.addBatch("INSERT INTO projects VALUES (1,'projekt 1')" );
            st.addBatch("INSERT INTO projects VALUES (2,'projekt 2')" );

            st.addBatch("INSERT INTO projectrelations VALUES (1,1,1)");
            st.addBatch("INSERT INTO projectrelations VALUES (2,1,2)");
            st.addBatch("INSERT INTO projectrelations VALUES (3,2,2)");

            int[] updateCounts = st.executeBatch();

            // end transaction
            connection.commit();
        }
        catch (Exception e)
        {
            System.out.println("Fail in JdbcTestFixture - setup");
            System.out.println(e.getMessage());
        }
        finally
        {
            connection.close();
        }
    }

    public void tearDown() throws SQLException {
        connection.close();
    }
}
