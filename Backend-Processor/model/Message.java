package com.projeto_01.chat.Processor.model;

public class Message {
    private String name;
    private String message;

    public Message() {
    }

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void saveMessage(String message) {
        this.message = message;
    }
}
