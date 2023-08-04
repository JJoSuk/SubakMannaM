package kr.co;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SubakMannaM {

    public static void main(String[] args) {
        SpringApplication.run(SubakMannaM.class, args);
    }

    @Bean
    public String uploadPath(){
        return "C:/image/";
    }
}