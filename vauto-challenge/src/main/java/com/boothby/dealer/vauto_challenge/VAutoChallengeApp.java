package com.boothby.dealer.vauto_challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.boothby.dealer.vauto_challenge.config.AppConfig;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_CompletableFuture;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_OOP;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_ParallelStream;

@SpringBootApplication
public class VAutoChallengeApp {

    @Autowired
    private VAutoChallenge_CompletableFuture vAutoChallenge_CompletableFuture;
    @Autowired
    private VAutoChallenge_ParallelStream vAutoChallenge_ParallelStream;
    @Autowired
    private VAutoChallenge_OOP vAutoChallenge_OOP;
    
    public static void main(String[] args) {
        SpringApplication.run(VAutoChallengeApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            AppConfig appConfig = (AppConfig)ctx.getBean("appConfig");
            switch(appConfig.getApiSolution()) {
                case "OOP":
                    vAutoChallenge_OOP.process();
                    break;

                case "CompletableFuture":
                    vAutoChallenge_CompletableFuture.process();
                    break;
                
                case "ParallelStream":
                    vAutoChallenge_ParallelStream.process();
                    break;
            }
        };
    }
}
