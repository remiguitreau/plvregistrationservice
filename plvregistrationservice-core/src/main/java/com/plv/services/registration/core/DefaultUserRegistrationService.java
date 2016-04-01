package com.plv.services.registration.core;

import com.plv.services.registration.api.contracts.UserRegistrationService;
import com.plv.services.registration.api.model.ProcessState;
import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;

import java.util.Collection;
import java.util.LinkedList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultUserRegistrationService implements UserRegistrationService, UserRegistrarRegistry {

    private final UserRegistrar newsLetterRegistrar;

    private final Collection<UserRegistrar> registrars = new LinkedList<>();

    @Override
    public UserRegistrationReport registerNewUser(final User user, final boolean registerOnNewsLetter) {
        log.info("Launch user registration: {} (to newsLetter ? {})", user, registerOnNewsLetter);
        final UserRegistrationReport report = UserRegistrationReport.newUserRegistrationReport(user);
        registerUserOnEachRegistrar(user, report);
        report.withRegistrationStatus(newsLetterRegistrar.getType(),
                registerOnNewsLetterIfNeeded(user, registerOnNewsLetter));
        return report;
    }

    @Override
    public void register(final UserRegistrar userRegistrar) {
        registrars.add(userRegistrar);
    }

    private void registerUserOnEachRegistrar(final User user, final UserRegistrationReport report) {
        registrars.forEach(registrar -> report.withRegistrationStatus(registrar.getType(),
                processRegistration(user, registrar)));
    }

    private ProcessState registerOnNewsLetterIfNeeded(final User user, final boolean registerOnNewsLetter) {
        if (registerOnNewsLetter) {
            return processRegistration(user, newsLetterRegistrar);
        }
        return ProcessState.SKIPPED;
    }

    private ProcessState processRegistration(final User user, final UserRegistrar registrar) {
        try {
            registrar.register(user);
            return ProcessState.SUCCESS;
        } catch (final Exception ex) {
            log.warn("User registration failed on service '" + registrar.getType() + "'", ex);
        }
        return ProcessState.FAILED;
    }
}
