package com.projeto_01.chat.Processor.bo;

import com.projeto_01.chat.Processor.dao.MessageDao;
import com.projeto_01.chat.Processor.model.Message;

public class MessageBo {
    private MessageDao messageDao;

    public MessageBo() {
        messageDao = new MessageDao();
    }

    void saveMessage(String messageString) {
        String[] data = messageString.split(":", 2);
        Message message = new Message(data[0], data[1]);
    }
}