package demo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Project {
    private String projectName;
    private int projectid;
    private LocalDate expStartDate;
    private LocalDate expEndDate;
    private List<SubProject> subProjects;

    public Project(String projectName, LocalDate expStartDate, LocalDate expEndDate, List<SubProject> subProjects) {
        this.projectName = projectName;
        this.expStartDate = expStartDate;
        this.expEndDate = expEndDate;
        this.subProjects = subProjects;
    }

    public Project() {
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public LocalDate getExpStartDate() {
        return expStartDate;
    }

    public void setExpStartDate(LocalDate expStartDate) {
        this.expStartDate = expStartDate;
    }

    public LocalDate getExpEndDate() {
        return expEndDate;
    }

    public void setExpEndDate(LocalDate expEndDate) {
        this.expEndDate = expEndDate;
    }

    public double calWorkHoursPerDay () {

        double totalProjectHours = calProjectTotalTime();

        int dif = (int) ChronoUnit.DAYS.between(expStartDate,expEndDate);
        dif = Math.abs(dif);

        double workHoursPerDay;

        if(dif == 0){
            workHoursPerDay = totalProjectHours;
        } else{
            workHoursPerDay = totalProjectHours / dif;
        }

        return workHoursPerDay;
    }

    //convert project hours per day to an integer
    public int ConvertProjectHoursPerDayToAbs(){
        int hours = (int) calWorkHoursPerDay();
        return Math.abs(hours);
    }

    //convert the remaining time to minutes
    public int ConvertProjectHoursToMinutesPerDay(){
        double res = calWorkHoursPerDay() % 1;
        int minutes = (int) (res * 60);
        return minutes;
    }

    //Calculate the total time estimated on a project
    public int calProjectTotalTime() {

        int totalTime = 0;

        for (int i = 0; i < subProjects.size(); i++) {
            SubProject subProject = subProjects.get(i);
            totalTime += subProject.calSubProjectTotalTime();
        }

        return totalTime;
    }

    //Calculate the total hours the logged in user used on a project
    public int calUserWorkHoursOnProject (int userid){
        int sum =0;
        for (int i = 0; i < subProjects.size(); i++) {
            for (int j = 0; j < subProjects.get(i).getTasks().size(); j++) {
                for (int k = 0; k < subProjects.get(i).getTasks().get(j).getTaskMembers().size(); k++) {
                    if(subProjects.get(i).getTasks().get(j).getTaskMembers().get(k).getUserid() == userid){
                        sum += subProjects.get(i).getTasks().get(j).getTimeEstimation();
                    }
                }
            }
        }

        return sum;
    }

}
