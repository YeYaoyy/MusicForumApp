package edu.northeastern.numad22fa_team23;

public class User {
    public String getUsername() {
        return username;
    }

    //    @IgnoreExtraProperties
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username) {
        this.username = username;
    }


}
