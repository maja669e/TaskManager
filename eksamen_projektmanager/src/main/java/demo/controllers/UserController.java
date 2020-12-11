package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import demo.service.TaskService;
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

    @PostMapping("/login")
    public String loginUser(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.Login(userName, password);
        setSessionInfo(request, user);
        return "redirect:/projekt_oversigt";
    }

    @GetMapping("/hold")
    public String hold(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        if (user == null) {
            return "redirect:/";
        } else {
            int userTeamId = userService.getUserTeamId(user.getUserid());

            Team team = userService.getTeam(userTeamId);

            model.addAttribute("team", team);

            return "hold";
        }
    }

    @GetMapping("/tidsforbrug")
    public String tidsforbrug(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {
            //Get all projects
            List<Project> projects = projectService.getProjects(user.getUserid());

            int subprojectid;
            int taskid;

            //Populate project with data from db
            for (Project project:projects) {
                List<SubProject> subProjects = userService.getSubProjects(project.getProjectid());
                project.setSubProjects(subProjects);

                for (int i = 0; i < subProjects.size(); i++) {
                    subprojectid = subProjects.get(i).getSubProjectID();
                    if (subProjects.get(i).getSubProjectID() == subprojectid) {
                        subProjects.get(i).setTasks(userService.getTasks(subprojectid));
                    }
                }

                for (int i = 0; i < subProjects.size(); i++) {
                    for (int j = 0; j < subProjects.get(i).getTasks().size(); j++) {
                        taskid = subProjects.get(i).getTasks().get(j).getTaskId();
                        subProjects.get(i).getTasks().get(j).setTaskMembers(userService.getTaskMembers(taskid));
                    }
                }
            }

            int projectTimeUser;
            List<Integer> timeSpendOnProjects = new ArrayList<>();
            for (Project project: projects) {
                projectTimeUser= project.calUserWorkHoursOnProject(user);
                timeSpendOnProjects.add(projectTimeUser);
            }

            Map<String, Integer> otherMap = new HashMap<>();

            for (int i = 0; i < projects.size(); i++) {
                otherMap.put(projects.get(i).getProjectName(), timeSpendOnProjects.get(i));
            }

            List<String> keys = new ArrayList<>();
            for (int i = 0; i < projects.size(); i++) {
                keys.add((projects.get(i).getProjectName()));
            }

            model.addAttribute("map", keys);
            model.addAttribute("otherMap", otherMap);
            model.addAttribute("projects", projects);

            return "tidsforbrug";
        }
    }

    private void setSessionInfo(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }
}
