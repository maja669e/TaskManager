package demo.model;

public class SubProject {
    private String subProjectName;
    private int subprojectid;

    public SubProject(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public int getSubprojectid() {
        return subprojectid;
    }

    public void setSubprojectid(int subprojectid) {
        this.subprojectid = subprojectid;
    }
    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }
}
