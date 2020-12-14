package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.*;
import demo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;

@Controller
public class TaskController {

    private TaskService taskService = new TaskService(new DataFacadeImpl());

    @PostMapping("addMemberToTask")
    public String addMemberToTask(WebRequest request) throws ProjectManagerException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        if (user == null) {
            return "redirect:/";
        } else {
            //Retrieve values from HTML form via WebRequest
            String userName = request.getParameter("username");
            int taskid = Integer.parseInt(request.getParameter("taskid"));

            if (!userName.equals("placeholder")) {
                Task task = taskService.getTask(taskid);
                task.setTaskMembers(taskService.getTaskMembers(taskid));

                User taskUser = taskService.getTaskUser(userName);
                HashMap<String, User> hashMap = new HashMap<>();

                for (int i = 0; i < task.getTaskMembers().size(); i++) {
                    hashMap.put(task.getTaskMembers().get(i).getUserName(), task.getTaskMembers().get(i));
                }


                if (!hashMap.containsKey(userName)) {
                    taskService.addMemberToTask(taskid, taskUser.getUserid());
                }
            }

            return "redirect:/projekt";
        }
    }

    @PostMapping("deleteTaskMemberFromTask")
    public String deleteTaskMemberFromTask(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int userid = Integer.parseInt(request.getParameter("userid"));
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        if(userid != 0){
            taskService.deleteMemberFromTask(taskid, userid);
        }

        return "redirect:/projekt";
    }

    @PostMapping("addTask")
    public String addTask(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        String taskName = request.getParameter("taskname");
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));

        taskService.addTask(project, subprojectid, taskName);

        return "redirect:/projekt";
    }

    @PostMapping("deleteTask")
    public String deleteTask(WebRequest request) throws ProjectManagerException {
        int taskid = Integer.parseInt(request.getParameter("taskid"));
        taskService.deleteTask(taskid);

        return "redirect:/projekt";
    }

    @PostMapping("setTaskstatus")
    public String setTaskstatus(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        Task task = taskService.getTask(taskid);

        if (task.getTaskStatus() == 2) {
            task.setTaskStatus(1);
        } else if (task.getTaskStatus() == 1) {
            task.setTaskStatus(0);
        } else {
            task.setTaskStatus(2);
        }

        taskService.setTaskstatus(taskid, task.getTaskStatus());
        return "redirect:/projekt";
    }

    @PostMapping("editTask")
    public String editTask(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String taskName = request.getParameter("taskName");
        int timeEstimate = Integer.parseInt(request.getParameter("timeEstimate"));
        String deadline = request.getParameter("deadline");
        int taskid = Integer.parseInt(request.getParameter("taskid"));

        taskService.editTask(taskid, taskName, timeEstimate, deadline);

        return "redirect:/projekt";
    }

}
