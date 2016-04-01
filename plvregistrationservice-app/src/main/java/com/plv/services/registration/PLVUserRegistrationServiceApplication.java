package com.plv.services.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class PLVUserRegistrationServiceApplication {

    public static void main(final String[] args) {
        log.info("Launching PLV registration service...");
        SpringApplication.run(PLVUserRegistrationServiceApplication.class, args);
        log.info("...PLV registration service started !!! Let's play.");
    }
}