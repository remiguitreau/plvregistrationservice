package com.plv.services.registration.registrars.mailchimp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plv.services.registration.core.UserRegistrar;

@Configuration
public class MailChimpConfiguration {

    @Bean(name = "newsletterRegistrar")
    public UserRegistrar mailchimpUserRegistrar() {
        return new MailChimpUserRegistrar();
    }

}
