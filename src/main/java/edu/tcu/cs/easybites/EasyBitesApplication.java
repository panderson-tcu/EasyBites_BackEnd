package edu.tcu.cs.easybites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyBitesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyBitesApplication.class, args);
    }

//    @Bean
//    public IdWorker idWorker() {
//        return new IdWorker(1,1);
//    }

}