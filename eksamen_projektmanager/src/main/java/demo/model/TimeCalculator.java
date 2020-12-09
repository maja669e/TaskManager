package demo.model;

import java.time.temporal.ChronoUnit;

public class TimeCalculator {

    public int calSubProjectTotalTime(SubProject subProject) { //all subprojects for a single project

        int totalTime = 0;
        for (int i = 0; i < subProject.getTasks().size(); i++) {
            totalTime += subProject.getTasks().get(i).getTimeEstimation();
        }

        return totalTime;
    }

    public int calProjectTotalTime(Project project) {

        int totalTime = 0;

        for (int i = 0; i < project.getSubProjects().size(); i++) {
            totalTime += calSubProjectTotalTime(project.getSubProjects().get(i));
        }

        return totalTime;
    }

    public int calWorkHoursPerDay (Project project){

        int totalProjectHours = calProjectTotalTime(project);

        int dif = (int) ChronoUnit.DAYS.between(project.getExpStartDate(),project.getExpEndDate());
        dif = Math.abs(dif);

        int workHoursPerDay;

        if(dif == 0){
            workHoursPerDay = totalProjectHours;
        } else{
            workHoursPerDay = totalProjectHours / dif;
        }

        return workHoursPerDay;
    }

    public int calUserWorkHoursOnProject (Project project, int userid){

        int sum =0;
        for (int i = 0; i < project.getSubProjects().size(); i++) {
            if(project.getSubProjects().get(i).getTasks().get(i).getTaskMembers().get(i).getUserid() == userid){
                sum += project.getSubProjects().get(i).getTasks().get(i).getTimeEstimation();
            }
        }

        return sum;
    }

}
