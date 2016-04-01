package com.plv.services.registration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plv.services.registration.core.UserRegistrar;
import com.plv.services.registration.core.UserRegistrarRegistry;

@Configuration
public class UserRegistrationServiceTestConfiguration {

    @Bean(name = "registrar1")
    public UserRegistrar firstRegistrar(final UserRegistrarRegistry registrarRegistry) {
        final UserRegistrar registrar = Mockito.mock(UserRegistrar.class);
        Mockito.when(registrar.getType()).thenReturn("registrar1");
        registrarRegistry.register(registrar);
        return registrar;
    }

    @Bean(name = "registrar2")
    public UserRegistrar secondRegistrar(final UserRegistrarRegistry registrarRegistry) {
        final UserRegistrar registrar = Mockito.mock(UserRegistrar.class);
        Mockito.when(registrar.getType()).thenReturn("registrar2");
        registrarRegistry.register(registrar);
        return registrar;
    }
}
