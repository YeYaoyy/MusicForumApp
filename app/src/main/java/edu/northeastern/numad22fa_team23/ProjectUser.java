package edu.northeastern.numad22fa_team23;

public class ProjectUser {
    public String getUsername() {
        return username;
    }

    public void ProjectUser() {}

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public ProjectUser(String username, String uid) {
        this.uid = uid;
        this.username = username;
    }

}
