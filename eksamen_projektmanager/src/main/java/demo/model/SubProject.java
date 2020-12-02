package demo.model;

public class SubProject {
    private String subProjectName;
    private int subProjectID;

    public int getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public SubProject(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }
}
