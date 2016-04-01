package com.plv.services.registration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plv.services.registration.core.DefaultUserRegistrationService;
import com.plv.services.registration.core.UserRegistrar;

@Configuration
public class UserRegistrationServiceConfiguration {

    @Bean
    public DefaultUserRegistrationService userRegistrationService(@Qualifier("newsletterRegistrar")
    final UserRegistrar newsLetterRegistrar) {
        return new DefaultUserRegistrationService(newsLetterRegistrar);
    }

}
