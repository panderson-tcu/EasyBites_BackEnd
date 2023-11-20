package edu.tcu.cs.easybites;

import edu.tcu.cs.easybites.recipe.dto.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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