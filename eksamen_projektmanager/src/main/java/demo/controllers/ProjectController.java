package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import demo.service.ProjectService;
import demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    private ProjectService projectService = new ProjectService(new DataFacadeImpl());

    @GetMapping("/projekt_oversigt")
    public String displayProjects(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {
            //Get all projects
            List<Project> projects = projectService.getProjects(user.getUserid());
            model.addAttribute("projects", projects);
            return "projekt_oversigt";
        }
    }

    @PostMapping("addProject")
    public String addProject(WebRequest request) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        Project project = projectService.addProject(user.getUserid());
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    @PostMapping("deleteProject")
    public String deleteProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid_del"));
        projectService.deleteProject(projectid);

        return "redirect:/projekt_oversigt";
    }

    @PostMapping("getProject")
    public String getProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid"));
        Project project = projectService.getSingleProject(projectid);
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    @PostMapping("editProject")
    public String editProject(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        String newProjectName = request.getParameter("projectName");
        String enddateTemp = request.getParameter("enddate");

        project.setProjectName(newProjectName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate enddate = LocalDate.parse(enddateTemp, formatter);
        project.setExpEndDate(enddate);

        projectService.editProject(project.getProjectid(), newProjectName, enddateTemp);

        return "redirect:/projekt";
    }

    @GetMapping("/projekt")
    public String project(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        System.out.println(project.getSubProjects().size() + " test");
//check if project is null
        if (user == null) {
            return "redirect:/";
        } else {

            int projectTotalTimeConsumtion = project.calProjectTotalTime();
            int workHoursPerDay = project.calWorkHoursPerDay();
           // int userTeamId = userService.getUserTeamId(user.getUserid());
            //Team team = userService.getTeam(userTeamId);

            //model.addAttribute("team", team);
            model.addAttribute("workHoursPerDay", workHoursPerDay);
            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("projectTime", projectTotalTimeConsumtion);
            //model.addAttribute("subProjects", subProjects);
            model.addAttribute("project", project);
            return "projekt";
        }
    }

    private void setSessionProject(WebRequest request, Project project) {
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
    }
}
