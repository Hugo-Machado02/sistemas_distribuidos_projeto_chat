package com.projeto_01.chat.bo;

import java.util.List;

import com.projeto_01.chat.dao.MessageDao;
import com.projeto_01.chat.model.Message;

public class MessageBo {
    private MessageDao messageDao;

    public MessageBo() {
        messageDao = new MessageDao();
    }

    public void saveMessage(String message) {
        String[] parts = message.split(":", 2);
        String name = parts[0].trim();
        String text = parts[1].trim();
        
        Message messageObj = new Message(name, text);
        messageDao.save(messageObj);
    }

    public List<Message> getAllMessages() {
        return messageDao.getAll();
    }
}