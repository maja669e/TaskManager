package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import demo.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ProjectController {

    private ProjectService projectService = new ProjectService(new DataFacadeImpl());


    /**
     * @author Maja Bijedic
     */
    @GetMapping("/projekt_oversigt")
    public String displayProjects(WebRequest request, Model model) throws ProjectManagerException {
        // Retrieve object from web request (session scope)
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {
            //Get all projects
            List<Project> projects = projectService.getProjects(user.getUserid());
            for (Project project: projects) {
                Project projectObj = projectService.getProject(project.getProjectid());
                project.setSubProjects(projectObj.getSubProjects());
            }
            
            setSessionProjects(request, projects);
            model.addAttribute("projects", projects);
            return "projekt_oversigt";
        }
    }


    /**
     * @author Nicolai Okkels
     */
    @PostMapping("addProject")
    public String addProject(WebRequest request) throws ProjectManagerException {
        // Retrieve object from web request (session scope)
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        Project project = projectService.addProject(user.getUserid());
        setSessionProject(request, project);

        return "redirect:/projekt";
    }


    /**
     * @author Maja Bijedic
     */
    @PostMapping("deleteProject")
    public String deleteProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid_del"));
        projectService.deleteProject(projectid);

        return "redirect:/projekt_oversigt";
    }


    /**
     * @author Nicolai Okkels
     */
    @PostMapping("getProject")
    public String getProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid"));
        Project project = projectService.getProject(projectid);
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    /**
     * @author Nicolai Okkels
     */
    @PostMapping("editProject")
    public String editProject(WebRequest request) throws ProjectManagerException {
        // Retrieve object from web request (session scope)
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        String newProjectName = request.getParameter("projectName");
        String enddateTemp = request.getParameter("enddate");

        project.setProjectName(newProjectName);

        //Format from string to localDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate enddate = LocalDate.parse(enddateTemp, formatter);
        project.setExpEndDate(enddate);

        projectService.editProject(project.getProjectid(), newProjectName, enddateTemp);

        return "redirect:/projekt";
    }


    /**
     * @author Nicolai Okkels
     */
    @GetMapping("/projekt")
    public String project(WebRequest request, Model model) throws ProjectManagerException {
        // Retrieve object from web request (session scope)
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        Team team = (Team) request.getAttribute("team", WebRequest.SCOPE_SESSION);

        if (user == null || project == null) {
            return "redirect:/";
        } else {
            project = projectService.getProject(project.getProjectid());
            setSessionProject(request,project);

            model.addAttribute("team", team);
            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("project", project);

            return "projekt";
        }
    }

    private void setSessionProject(WebRequest request, Project project) {
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
    }

    private void setSessionProjects(WebRequest request, List<Project> projects){
        request.setAttribute("projects", projects, WebRequest.SCOPE_SESSION);
    }
}
