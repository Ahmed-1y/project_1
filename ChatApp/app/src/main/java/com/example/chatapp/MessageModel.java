package com.example.chatapp;

public class MessageModel {

    public String messageid;

    public String senderid;
    public String message;

    public MessageModel() {

    }


    public MessageModel(String messageid, String senderid, String message) {
        this.messageid = messageid;
        this.senderid = senderid;
        this.message = message;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
