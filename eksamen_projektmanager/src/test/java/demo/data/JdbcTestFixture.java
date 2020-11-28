package demo.data;

import java.sql.*;

public class JdbcTestFixture {

    static final String USER = "root";
    static final String PWD = "root";
    static final String URL = "jdbc:mysql://localhost/projektellotest?serverTimezone=UTC"; //needs to make schema in workbench named projektellotest
    Connection connection;

    public void setUp() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            Statement st = connection.createStatement();
            // start transaction
            connection.setAutoCommit(false);

            // create

            st.addBatch("DROP TABLE if exists projects");
            st.addBatch("create table projects(" +
                    "   projectid       integer not null primary key," +
                    "   projectname     varchar(100) not null)"
            );

            st.addBatch("DROP TABLE if exists users");
            st.addBatch("create table users(" +
                    "   userid      integer not null primary key," +
                    "   username    varchar(100) not null," +
                    "   password    varchar(100) not null," +
                    "   name        varchar(100) not null)");

            st.addBatch("DROP TABLE if exists projectrelations");
            st.addBatch("create table projectrelations(" +
                    "   projectrelationid   integer not null primary key," +
                    "   userid              integer not null," +
                    "   projectid           integer not null) ");

            st.addBatch("DROP TABLE if exists subprojects");
            st.addBatch("create table subprojects(" +
                    "   subprojectid          integer not null primary key," +
                    "   projectid             integer not null," +
                    "   subprojectname        varchar(100)) ");

            st.addBatch("DROP TABLE if exists tasks");
            st.addBatch("create table tasks(" +
                    "   taskid                integer not null primary key," +
                    "   projectid             integer," +
                    "   subprojectid          integer," +
                    "   taskname              varchar(100)," +
                    "   timeestimate          integer," +
                    "   deadline              date) ");

            st.addBatch("DROP TABLE if exists taskrelations");
            st.addBatch("create table taskrelations(" +
                    "   taskrelationid   integer not null primary key," +
                    "   taskid           integer not null," +
                    "   userid           integer not null) ");

            st.addBatch("DROP TABLE if exists teams");
            st.addBatch("create table teams(" +
                    "   teamid             integer not null primary key," +
                    "   teamname           varchar(100)) ");

            st.addBatch("DROP TABLE if exists teamrelations");
            st.addBatch("create table teamrelations(" +
                    "   teamrelationid   integer not null primary key," +
                    "   userid           integer not null," +
                    "   teamid           integer not null) ");


            // insert
            st.addBatch("INSERT INTO users VALUES (1,'test1', '1', 'Nicolai')");
            st.addBatch("INSERT INTO users VALUES (2,'test2', '1', 'Phuc')");
            st.addBatch("INSERT INTO users VALUES (3,'test3', '1', 'Maja')");
            st.addBatch("INSERT INTO users VALUES (4,'test4', '1', 'Bo')");
            st.addBatch("INSERT INTO users VALUES (5,'test5', '1', 'Hans')");

            st.addBatch("INSERT INTO projects VALUES (1,'projekt 1')");
            st.addBatch("INSERT INTO projects VALUES (2,'projekt 2')");

            st.addBatch("INSERT INTO projectrelations VALUES (1,1,1)");
            st.addBatch("INSERT INTO projectrelations VALUES (2,1,2)");
            st.addBatch("INSERT INTO projectrelations VALUES (3,2,2)");

            st.addBatch("INSERT INTO subprojects VALUES(1,1,'mini projekt 1')");
            st.addBatch("INSERT INTO subprojects VALUES(2,1,'mini projekt 2')");
            st.addBatch("INSERT INTO subprojects VALUES(3,1,'mini projekt 3')");
            //st.addBatch("INSERT INTO subprojects VALUES(1,2,'mini projekt 4')");

            st.addBatch("INSERT INTO tasks VALUES(1,1,2,'opgave 1', 20, '2021-02-02')");
            st.addBatch("INSERT INTO tasks VALUES(2,1,1,'opgave 2', 5, '2021-02-02')");
            st.addBatch("INSERT INTO tasks VALUES(3,1,3,'opgave 3', 12, '2021-02-02')");
            st.addBatch("INSERT INTO tasks VALUES(4,1,1,'opgave 4', 2, '2021-02-02')");
            st.addBatch("INSERT INTO tasks VALUES(5,1,2,'opgave 5', 25, '2021-02-02')");
            st.addBatch("INSERT INTO tasks VALUES(6,2,1,'opgave 1', 30, '2021-09-05')");

            st.addBatch("INSERT INTO teams VALUES(1,'hold 1')");
            st.addBatch("INSERT INTO teams VALUES(2,'hold 2')");

            st.addBatch("INSERT INTO teamrelations VALUE(1,1,1)");
            st.addBatch("INSERT INTO teamrelations VALUE(2,2,1)");
            st.addBatch("INSERT INTO teamrelations VALUE(3,3,2)");

            st.addBatch("INSERT INTO taskrelations VALUES(1,1,1)");
            st.addBatch("INSERT INTO taskrelations VALUES(2,2,1)");
            st.addBatch("INSERT INTO taskrelations VALUES(3,3,2)");
            st.addBatch("INSERT INTO taskrelations VALUES(4,1,2)");

            int[] updateCounts = st.executeBatch();

            // end transaction
            connection.commit();
        } catch (Exception e) {
            System.out.println("Fail in JdbcTestFixture - setup");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public void tearDown() throws SQLException {
        connection.close();
    }

    public void addProject() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            Statement st = connection.createStatement();
            // start transaction
            connection.setAutoCommit(false);


            st.addBatch("INSERT INTO projects VALUES (3,'projekt 3')");

            st.addBatch("INSERT INTO projectrelations VALUES (4,1,3)");

            int[] updateCounts = st.executeBatch();

            // end transaction
            connection.commit();

        } catch (Exception e) {
            System.out.println("Fail to add project to db");
            System.out.println(e.getMessage());
        } finally {
            connection.close();
        }
    }


}
