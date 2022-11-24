package edu.northeastern.numad22fa_team23;

public class Chat {
    public Chat(String content, String username, String time, String uid) {
        this.content = content;
        this.username = username;
        this.time = time;
        this.uid = uid;
    }
    public Chat() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String content;
    private String username;
    private String time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
}
