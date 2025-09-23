package com.projeto_01.chat.Processor.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_01.chat.Processor.model.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private final File storageFile = new File("src/main/resources/chatHistory/history.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Message> messages = new ArrayList<>();

    public MessageDao() {
        try {
            File folder = new File("src/main/resources/chatHistory");
            if (!folder.exists()) {
                folder.mkdirs(); // cria a pasta
            }

            if (!storageFile.exists()) {
                storageFile.createNewFile(); // cria o arquivo vazio
                mapper.writeValue(storageFile, messages); // inicializa com lista vazia
            }

            loadFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Salva nova mensagem
    public void save(Message message) {
        messages.add(message);
        saveToFile();
    }

    // Retorna todas as mensagens
    public List<Message> findAll() {
        return new ArrayList<>(messages);
    }

    // Carrega mensagens do arquivo JSON
    private void loadFromFile() {
        if (storageFile.exists()) {
            try {
                messages = mapper.readValue(storageFile, new TypeReference<List<Message>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Salva mensagens no arquivo JSON
    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(storageFile, messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
