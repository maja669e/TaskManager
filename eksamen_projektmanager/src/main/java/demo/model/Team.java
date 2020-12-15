package demo.model;

import java.util.List;


/**
 * @author Nicolai Okkels
 */
public class Team {
    private String teamName;
    private List<User> team;

    public Team() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<User> getTeam() {
        return team;
    }

    public void setTeam(List<User> team) {
        this.team = team;
    }
}
