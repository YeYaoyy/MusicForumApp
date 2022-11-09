package edu.northeastern.numad22fa_team23;

import java.util.List;

public class User {
    public String getUsername() {
        return username;
    }

    //    @IgnoreExtraProperties
    public String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String token;
    public List<Message> messageList;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username) {
        this.username = username;
    }

}
