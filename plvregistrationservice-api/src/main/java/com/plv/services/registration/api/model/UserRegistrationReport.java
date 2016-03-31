package com.plv.services.registration.api.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class UserRegistrationReport {

    private User user;

    private List<UserRegistrationStatus> status = new LinkedList<>();

    public static UserRegistrationReport newUserRegistrationReport(final User user) {
        final UserRegistrationReport userRegistrationReport = new UserRegistrationReport();
        userRegistrationReport.setUser(user);
        return userRegistrationReport;
    }

    public UserRegistrationReport withRegistrationStatus(final String registrationType,
            final ProcessState processState) {
        status.add(new UserRegistrationStatus(processState, registrationType));
        return this;
    }
}
