package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private UserService userService = new UserService(new DataFacadeImpl());

    @ExceptionHandler(ProjectManagerException.class)
    @GetMapping("/")
    public String login(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage());
        return "index";
    }

    /**
     * @author Maja Bijedic
     */
    @PostMapping("/login")
    public String loginUser(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = userService.Login(userName, password);
        int userTeamId = userService.getUserTeamId(user.getUserid());
        Team team = userService.getTeam(userTeamId);

        setSessionTeam(request, team);
        setSessionUser(request, user);
        return "redirect:/projekt_oversigt";
    }

    /**
     * @author Nicolai Okkels
     */
    @GetMapping("/hold")
    public String teamOverview(WebRequest request, Model model) {
        // Retrieve object from web request (session scope)
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Team team = (Team) request.getAttribute("team", WebRequest.SCOPE_SESSION);
        if (user == null) {
            return "redirect:/";
        } else {

            model.addAttribute("team", team);

            return "hold";
        }
    }

    /**
     * @author Nicolai Okkels
     */
    @GetMapping("/tidsforbrug")
    public String timeSpendOverview(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        List<Project> projects = (List<Project>) request.getAttribute("projects", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {

            model.addAttribute("userid", user.getUserid());
            model.addAttribute("projects", projects);

            return "tidsforbrug";
        }
    }

    private void setSessionUser(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }

    private void setSessionTeam(WebRequest request, Team team) {
        request.setAttribute("team", team, WebRequest.SCOPE_SESSION);
    }

}
