package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.model.Task;
import demo.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SubProjectController {

    private SubProjectService subProjectService = new SubProjectService(new DataFacadeImpl());

    @PostMapping("changeSubProjectName")
    public String changeSubProjectName(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String newSubProjectName = request.getParameter("subProjectName");
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        subProjectService.changeSubProjectName(subprojectid,newSubProjectName);

        return "redirect:/projekt";
    }

    @PostMapping("addSubProject")
    public String addSubProject(WebRequest request) throws ProjectManagerException {
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);
        List<Task> tasks = new ArrayList<>(); //TODO: fejl?
        //Retrieve values from HTML form via WebRequest

        subProjectService.addSubProject(project, tasks);

        return "redirect:/projekt";
    }

    @PostMapping("deleteSubProject")
    public String deleteSubProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        subProjectService.deleteSubproject(subprojectid);

        return "redirect:/projekt";
    }



}
