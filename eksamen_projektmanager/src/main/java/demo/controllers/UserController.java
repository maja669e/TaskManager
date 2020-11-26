package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@Controller
public class UserController {

    //use case controller (GRASP Controller) - injects concrete facade instance into controller
    private UserService userService = new UserService(new DataFacadeImpl());

    @GetMapping("/")
    public String login() {
        return "index";
    }

    @GetMapping("/opret_projekt")
    public String createProject() {
        return "opret_projekt";
    }

    @PostMapping("addProject")
    public String addProject(WebRequest request) throws ProjectManagerException {
    //Retrieve values from HTML form via WebRequest

        Project project = userService.addProject();
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    private void setSessionProject(WebRequest request, Project project) {
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
    }
}
