package edu.northeastern.numad22fa_team23;

import java.util.List;

//group class
public class ProjectGroup {
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }

//    public List<Chat> getChats() {
//        return chats;
//    }
//
//    public void setChats(List<Chat> chats) {
//        this.chats = chats;
//    }

    public String groupName;
    public String description;
    public List<Moment> moments;
//    public List<Chat> chats;


}
