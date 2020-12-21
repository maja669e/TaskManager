package demo.controllers;

import demo.data.DataFacadeImpl;
import demo.model.Project;
import demo.model.ProjectManagerException;
import demo.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


/**
 * @author Phuc Nguyen
 */
@Controller
public class SubProjectController {

    private SubProjectService subProjectService = new SubProjectService(new DataFacadeImpl());

    /**
     * gets a new subproject name and subproject id and edit it
     */
    @PostMapping("changeSubProjectName")
    public String changeSubProjectName(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        String newSubProjectName = request.getParameter("subProjectName");
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        subProjectService.changeSubProjectName(subprojectid,newSubProjectName);
        return "redirect:/projekt";
    }

    /**
     * takes the project in session and add a subproject
     */
    @PostMapping("addSubProject")
    public String addSubProject(WebRequest request) throws ProjectManagerException {
        // Retrieve object from web request (session scope)
        Project project = (Project) request.getAttribute("project", WebRequest.SCOPE_SESSION);

        subProjectService.addSubProject(project);

        return "redirect:/projekt";
    }

    /**
     * delete a subproject depending on the id
     */
    @PostMapping("deleteSubProject")
    public String deleteSubProject(WebRequest request) throws ProjectManagerException {
        //Retrieve values from HTML form via WebRequest
        int subprojectid = Integer.parseInt(request.getParameter("subprojectid"));
        subProjectService.deleteSubproject(subprojectid);

        return "redirect:/projekt";
    }



}
