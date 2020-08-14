package com.boothby.dealer.vauto_challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.boothby.dealer.vauto_challenge.config.AppConfig;
import com.boothby.dealer.vauto_challenge.solution.completable_future.CompletableFutureSolution;
import com.boothby.dealer.vauto_challenge.solution.parallel_stream.ParallelStreamSolution;

@SpringBootApplication
public class VAutoChallengeApp {

    @Autowired
    private CompletableFutureSolution completableFutureSolution;
    @Autowired
    private ParallelStreamSolution parallelStreamSolution;
    
    public static void main(String[] args) {
        SpringApplication.run(VAutoChallengeApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            AppConfig appConfig = (AppConfig)ctx.getBean("appConfig");
            switch(appConfig.getApiSolution()) {
                case "CompletableFuture":
                    completableFutureSolution.process();
                    break;
                
                case "ParallelStream":
                    parallelStreamSolution.process();
                    break;
            }
            // Shutdown
            ((ConfigurableApplicationContext)ctx).close();
            System.exit(0);            
        };
    }
}
