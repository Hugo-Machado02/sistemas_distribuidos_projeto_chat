package com.projeto_01_gateway.chat.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_01_gateway.chat.gateway.model.Message;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class MessageController extends TextWebSocketHandler {

    private final int port = 6081;
    private final String ip = "localhost";
    private final ObjectMapper mapper = new ObjectMapper();
    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    //Responsavel por pegar todo o histórico
    private void requestHistory(WebSocketSession session) {
        try (Socket socket = new Socket(ip, port);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()))) {
            
            output.println("GET_HISTORY_CHAT");

            String json = input.readLine();

            if (json != null && !json.isEmpty()) {
                List<Message> messages = mapper.readValue(json, new TypeReference<List<Message>>() {});
                // Envia cada mensagem para o cliente via WebSocket
                for (Message msg : messages) {
                    session.sendMessage(new TextMessage(msg.getName() + ": " + msg.getMessage()));
                }
            }

            System.out.println("Histórico enviado para a sessão: " + session.getId());
        } catch (Exception e) {
            System.err.println("Erro ao buscar histórico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Responsável por salvar a mensagem
    private void saveMessage(String msg) {
        try (Socket socket = new Socket(ip, port);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
            output.println(msg);
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Client Connect: " + session.getId());
        requestHistory(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        System.out.println("Message received: " + msg + " from session: " + session.getId());
        
        try {
            saveMessage(msg);
            System.out.println("Message saved successfully");
            broadcast(msg, session);
            System.out.println("Message broadcasted successfully");
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void broadcast(String msg, WebSocketSession sender) {
        synchronized (sessions) {
            Set<WebSocketSession> toRemove = new HashSet<>();
            System.out.println("Broadcasting to " + sessions.size() + " sessions");
            for (WebSocketSession session : sessions) {
                if (!session.getId().equals(sender.getId()) && session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(msg));
                        System.out.println("Message sent to session: " + session.getId());
                    } catch (Exception e) {
                        System.err.println("Error sending to session " + session.getId() + ": " + e.getMessage());
                        toRemove.add(session);
                    }
                } else if (!session.isOpen()) {
                    toRemove.add(session);
                }
            }
            sessions.removeAll(toRemove);
            if (!toRemove.isEmpty()) {
                System.out.println("Removed " + toRemove.size() + " closed sessions");
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }
}
