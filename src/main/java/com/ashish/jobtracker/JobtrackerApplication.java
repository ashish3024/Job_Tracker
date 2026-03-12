package com.ashish.jobtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class JobtrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobtrackerApplication.class, args);
    }

}
