package demo.service;

import demo.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // facade to datasource layer
    private DataFacade facade = null;

    public UserService(DataFacade facade) {
        this.facade = facade;
    }

    public User Login(String userName, String password) throws ProjectManagerException {
        return facade.Login(userName, password);
    }

    public Team getTeam(int teamid) throws ProjectManagerException {
        return facade.getTeam(teamid);
    }

    public int getUserTeamId(int userid) throws ProjectManagerException {
        return facade.getUserTeamId(userid);
    }
}
