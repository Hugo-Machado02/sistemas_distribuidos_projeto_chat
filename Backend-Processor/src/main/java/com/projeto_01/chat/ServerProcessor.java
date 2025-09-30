package com.projeto_01.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_01.chat.bo.MessageBo;
import com.projeto_01.chat.model.Message;

public class ServerProcessor {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(6081)) {
            MessageBo messageBo = new MessageBo();

            System.out.println("Servidor Iniciado!");
            
            while (true) {
                Socket socket = server.accept();
                new Thread(() -> connectClient(socket, messageBo)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connectClient(Socket socket, MessageBo messageBo) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
            
            String message = input.readLine();
            
            if (message.equals("GET_HISTORY_CHAT")) {
                System.out.println("Enviando histórico...");
                ObjectMapper mapper = new ObjectMapper();
                List<Message> allMessages = messageBo.getAllMessages();
                String json = mapper.writeValueAsString(allMessages);
                output.println(json);

                System.out.println("Histórico enviado com " + allMessages.size() + " mensagens.");
            } else {
                messageBo.saveMessage(message);
                System.out.println("Salvando: " + message);
            }
            
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
