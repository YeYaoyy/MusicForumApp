package edu.northeastern.numad22fa_team23;

public class Message {

    public String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String receiver;
    public String time;
    public int imageId;

    public Message(String sender, String receiver, String time, int imageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.imageId = imageId;
    }
}
