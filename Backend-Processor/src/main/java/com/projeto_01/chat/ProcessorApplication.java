package com.projeto_01.chat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessorApplication implements CommandLineRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Inicia o ServerProcessor
        ServerProcessor.main(args);
    }
}