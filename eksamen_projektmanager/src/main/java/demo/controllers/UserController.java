package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
public class UserController {

    private UserService userService = new UserService(new DataFacadeImpl());
    //TODO: ved ikke om dette er rigtig
    private TimeCalculator timeCalculator = new TimeCalculator();

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

            //set task members for all subprojects
            int taskid;
            for (int i = 0; i < subProjects.size(); i++) {
                for (int j = 0; j < subProjects.get(i).getTasks().size(); j++) {
                    taskid = subProjects.get(i).getTasks().get(j).getTaskId();
                    subProjects.get(i).getTasks().get(j).setTaskMembers(userService.getTaskMembers(taskid));
                }
            }

            int projectTotalTimeConsumtion = timeCalculator.calProjectTotalTime(project);
            int workHoursPerDay = timeCalculator.calWorkHoursPerDay(project);
            int userTeamId = userService.getUserTeamId(user.getUserid());

            Team team = userService.getTeam(userTeamId);

            model.addAttribute("team", team);
            model.addAttribute("workHoursPerDay", workHoursPerDay);
            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("projectTime", projectTotalTimeConsumtion);
            model.addAttribute("subProjects", subProjects);
            model.addAttribute("project", project);
            return "projekt";
        }
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

        userService.editProject(project.getProjectid(), newProjectName, enddateTemp);

        return "redirect:/projekt";
    }

    @PostMapping("changeSubProjectName")
    public String changeSubProjectName(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String newSubProjectName = request.getParameter("subProjectName");
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        userService.changeSubProjectName(subprojectid,newSubProjectName);

        return "redirect:/projekt";
    }

    @PostMapping("editTask")
    public String editTask(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String taskName = request.getParameter("taskName");
        int timeEstimate = Integer.parseInt(request.getParameter("timeEstimate"));
        String deadline = request.getParameter("deadline");
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        userService.editTask(taskid, taskName,timeEstimate,deadline);

        return "redirect:/projekt";
    }

    @PostMapping("setTaskstatus")
    public String setTaskstatus(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        Task task = userService.getTask(taskid);

        if(task.getTaskStatus() == 2){
            task.setTaskStatus(1);
        } else if(task.getTaskStatus() == 1){
            task.setTaskStatus(0);
        } else {
            task.setTaskStatus(2);
        }

        userService.setTaskstatus(taskid, task.getTaskStatus());
        return "redirect:/projekt";
    }

    @PostMapping("addSubProject")
    public String addSubProject(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        List<Task> tasks = new ArrayList<>();
        //Retrieve values from HTML form via WebRequest

        userService.addSubProject(project, tasks);

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
        userService.deleteTask(taskid);

        return "redirect:/projekt";
    }

    @GetMapping("/tidsforbrug")
    public String tidsforbrug(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //Checks if user is logged in
        if (user == null) {
            return "redirect:/";
        } else {
            //Get all projects
            List<Project> projects = userService.getProjects(user.getUserid());

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
                projectTimeUser= timeCalculator.calUserWorkHoursOnProject(project,user);
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

    @PostMapping("addMemberToTask")
    public String addMemberToTask(WebRequest request, Model model) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        if (user == null) {
            return "redirect:/";
        } else {
            //Retrieve values from HTML form via WebRequest
            String userName = request.getParameter("username");
            int taskid = Integer.parseInt(request.getParameter("taskid"));

            Task task = userService.getTask(taskid);
            task.setTaskMembers(userService.getTaskMembers(taskid));
            User taskUser = userService.getUser(userName);

            HashMap<String, User> hashMap = new HashMap<>();

            for (int i = 0; i < task.getTaskMembers().size(); i++) {
                hashMap.put(task.getTaskMembers().get(i).getUserName(), task.getTaskMembers().get(i));
            }

            if(!hashMap.containsKey(userName)){
                userService.addMemberToTask(taskid, taskUser.getUserid());
            }

            return "redirect:/projekt";
        }
    }

    @PostMapping("deleteTaskMemberFromTask")
    public String deleteTaskMemberFromTask(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int userid = Integer.parseInt(request.getParameter("userid"));
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        System.out.println(userid);
        System.out.println(taskid);

        userService.deleteMemberFromTask(taskid,userid);

        return "redirect:/projekt";
    }

    private void setSessionProject(WebRequest request, Project project) {
        request.setAttribute("project", project, WebRequest.SCOPE_SESSION);
    }

    private void setSessionInfo(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }
}
