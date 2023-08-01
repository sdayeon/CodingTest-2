package com.example.codingtest2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CodingTest2Application {

    public static void main(String[] args) {
        SpringApplication.run(CodingTest2Application.class, args);
        log.info("http://localhost:8080/");
    }

}
