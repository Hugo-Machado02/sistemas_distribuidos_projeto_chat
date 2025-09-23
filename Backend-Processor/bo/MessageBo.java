package com.projeto_01.chat.Processor.bo;

import java.util.List;

import com.projeto_01.chat.Processor.dao.MessageDao;
import com.projeto_01.chat.Processor.model.Message;

public class MessageBo {
    private MessageDao messageDao;

    public MessageBo() {
        messageDao = new MessageDao();
    }

    public void saveMessage(String name, String text) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome de usuário é obrigatório.");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("A mensagem é obrigatória.");
        }

        Message message = new Message(name, text);
        messageDao.save(message);
    }

    public List<Message> getAllMessages() {
        return messageDao.findAll();
    }

    public List<Message> getMessagesByUser(String name) {
        return messageDao.findAll().stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .toList();
    }
}