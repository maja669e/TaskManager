package demo.model;

public class User {
    private String userName;
    private String password;
    private int userid;
    private String name;

    public User(String userName, String password, int userid, String name) {
        this.userName = userName;
        this.password = password;
        this.userid = userid;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
