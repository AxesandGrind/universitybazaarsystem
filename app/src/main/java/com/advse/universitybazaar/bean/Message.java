package com.advse.universitybazaar.bean;

public class Message {

    private int messageId;
    private String messageDesc;
    private String senderId;
    private String receiverId;


    public Message(int messageId, String messageDesc, String senderId, String receiverId) {
        this.messageId = messageId;
        this.messageDesc = messageDesc;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
