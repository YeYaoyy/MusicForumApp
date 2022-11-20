package edu.northeastern.numad22fa_team23;

public class Chat {
    public Chat(String content, String username, String time) {
        this.content = content;
        this.username = username;
        this.time = time;
    }

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

    String content;
    String username;
    String time;
}
