package com.projeto_01.chat.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProcessor {
    public static void main(String[] args) {
        try(ServerSocket processor = new ServerSocket(5862)){
            System.out.println("Aguardando conexão com o gateway");
            
            while (true) {
                // Aceita a nova conexão do Gateway a cada mensagem
                try (Socket gatewaySocket = processor.accept();
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(gatewaySocket.getInputStream()))) {
                    
                    System.out.println("Gateway conectado!");
                    String mensagem = entrada.readLine();
                    if (mensagem != null) {
                        System.out.println("-> Mensagem Recebida: " + mensagem);
                    }

                } catch (IOException e) {
                    System.err.println("Erro na comunicação com o Gateway: " + e.getMessage());
                }
            }
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }
}
