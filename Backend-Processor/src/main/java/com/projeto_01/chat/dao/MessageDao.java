package com.projeto_01.chat.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_01.chat.model.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private final File storageFile = new File("src/main/resources/chatHistory/history.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Message> messages = new ArrayList<>();

    //Construtor
    public MessageDao() {
        try {
            File folder = new File("src/main/resources/chatHistory");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (!storageFile.exists()) {
                storageFile.createNewFile();
                mapper.writeValue(storageFile, messages);
            }

            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Salva uma nova mensagem
    public void save(Message message) {
        messages.add(message);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(storageFile, messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Retorna todas as mensagens
    public List<Message> getAll() {
        return new ArrayList<>(messages);
    }

    // BUsca mensagens do arquivo JSON
    private void loadFromFile() {
        if (storageFile.exists() && storageFile.length() > 0) {
            try {
                messages = mapper.readValue(storageFile, new TypeReference<List<Message>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
