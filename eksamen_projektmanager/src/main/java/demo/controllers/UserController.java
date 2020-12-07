package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private UserService userService = new UserService(new DataFacadeImpl());
    //TODO: ved ikke om dette er rigtig
    private TimeConsumtionCalculator timeConsumtionCalculator = new TimeConsumtionCalculator();

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

    @GetMapping("/projekt_oversigt")
    public String displayProjects(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {
            //Get all projects
            List<Project> projects = userService.getProjects(user.getUserid());
            model.addAttribute("projects", projects);
            return "projekt_oversigt";
        }
    }

    @PostMapping("addProject")
    public String addProject(WebRequest request) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        Project project = userService.addProject(user.getUserid());
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    @PostMapping("deleteProject")
    public String deleteProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid_del"));
        userService.deleteProject(projectid);

        return "redirect:/projekt_oversigt";
    }

    @PostMapping("getProject")
    public String getProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int projectid = Integer.parseInt(request.getParameter("projectid"));
        Project project = userService.getSingleProject(projectid);
        setSessionProject(request, project);

        return "redirect:/projekt";
    }

    @GetMapping("/projekt")
    public String project(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        if (user == null) {
            return "redirect:/";
        } else {
            List<SubProject> subProjects = userService.getSubProjects(project.getProjectid());
            project.setSubProjects(subProjects);

            //TODO dette skulle måske være et andet sted
            int subprojectid;
            for (int i = 0; i < subProjects.size(); i++) {
                subprojectid = subProjects.get(i).getSubProjectID();
                if (subProjects.get(i).getSubProjectID() == subprojectid) {
                    subProjects.get(i).setTasks(userService.getTasks(subprojectid));
                }
            }

            int projectTotalTimeConsumtion = timeConsumtionCalculator.calProjectTotalTime(project);

            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("projectTime", projectTotalTimeConsumtion);
            model.addAttribute("subProjects", subProjects);
            model.addAttribute("project", project);
            return "projekt";
        }
    }

    @PostMapping("changeProjectName")
    public String changeProjectName(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        String newProjectName = request.getParameter("projectName");

        project.setProjectName(newProjectName);

        userService.changeProjectName(project.getProjectid(), newProjectName);

        return "redirect:/projekt";
    }

    @PostMapping("changeSubProjectName")
    public String changeSubProjectName(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        //Retrieve values from HTML form via WebRequest
        String newSubProjectName = request.getParameter("subProjectName");

        //userService.changeSubProjectName();
        System.out.println(newSubProjectName);

        return "redirect:/projekt";
    }

    @PostMapping("editProject")
    public String editProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String taskName = request.getParameter("taskName");
        int timeEstimate = Integer.parseInt(request.getParameter("timeEstimate"));
        String deadline = request.getParameter("deadline");
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        userService.editTask(taskid, taskName,timeEstimate,deadline);

        return "redirect:/projekt";
    }


    /*@PostMapping("getProjectTask")
    public String getProjectTask(WebRequest request, Model model, RedirectAttributes redirectAttributes) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        List<SubProject> subProjects = userService.getSubProjects(project.getProjectid());

        for (int i = 0; i < subProjects.size(); i++) {
            List<Task> tasks = userService.getTasks(subProjects.get(i).getSubProjectID());
            model.addAttribute("tasks", tasks);
        }

        redirectAttributes.addFlashAttribute("message", "Der er ingen opgaver endnu");

        return "redirect:/projekt";
    }*/

    @PostMapping("addSubProject")
    public String addSubProject(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        List<Task> tasks = new ArrayList<>();
        //Retrieve values from HTML form via WebRequest
        String subprojectName = request.getParameter("subprojectname");

        userService.addSubProject(project, subprojectName, tasks);

        return "redirect:/projekt";
    }

    @PostMapping("deleteSubProject")
    public String deleteSubProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        userService.deleteSubproject(subprojectid);

        return "redirect:/projekt";
    }

    @PostMapping("addTask")
    public String addTask(WebRequest request, Model model) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        List<SubProject> subProjects = userService.getSubProjects(project.getProjectid());
        SubProject subProject = null;
        String taskName = request.getParameter("taskname");
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        System.out.println(subprojectid);

        for (int i = 0; i < subProjects.size(); i++) {
            if (subProjects.get(i).getSubProjectID() == subprojectid) {
                subProject = subProjects.get(i);
            }
        }

        userService.addTask(project, subProject, taskName);

        return "redirect:/projekt";
    }

    @PostMapping("deleteTask")
    public String deleteTask(WebRequest request) throws ProjectManagerException {
        int taskid = Integer.parseInt(request.getParameter("taskid"));
        System.out.println(taskid);
        userService.deleteTask(taskid);

        return "redirect:/projekt";
    }


    private void setSessionProject(WebRequest request, Project project) {
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
    }

    private void setSessionInfo(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }
}
