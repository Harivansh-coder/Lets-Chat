package com.harivansh.letschat.model;


public class Messages {

    String messageId;
    String messageDatabaseId;
    String messageText;
    Long messageTime;

    public Messages(String messageId, String messageText, Long messageTime) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public Messages(String messageId, String messageText) {
        this.messageId = messageId;
        this.messageText = messageText;
    }

    public Messages(String messageId, String messageDatabaseId, String messageText, Long messageTime) {
        this.messageId = messageId;
        this.messageDatabaseId = messageDatabaseId;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public Messages() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageDatabaseId() {
        return messageDatabaseId;
    }

    public void setMessageDatabaseId(String messageDatabaseId) {
        this.messageDatabaseId = messageDatabaseId;
    }
}
