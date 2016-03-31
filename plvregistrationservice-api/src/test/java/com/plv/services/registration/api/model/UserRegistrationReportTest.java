package com.plv.services.registration.api.model;

import org.junit.Assert;
import org.junit.Test;

public class UserRegistrationReportTest {

    @Test
    public void build_user_registration_without_status_is_correct() {
        final User user = User.builder().email("email").firstname("firstname").mobile("mobile").name(
                "name").zipcode("zipcode").build();
        final UserRegistrationReport report = UserRegistrationReport.newUserRegistrationReport(user);

        Assert.assertEquals(user, report.getUser());
        Assert.assertTrue(report.getStatus().isEmpty());
    }

    @Test
    public void build_user_registration_with_status_is_correct() {
        final User user = User.builder().email("email").firstname("firstname").mobile("mobile").name(
                "name").zipcode("zipcode").build();
        final UserRegistrationReport report = UserRegistrationReport.newUserRegistrationReport(
                user).withRegistrationStatus("reg1", ProcessState.SKIPPED).withRegistrationStatus("reg2",
                        ProcessState.SUCCESS);

        Assert.assertEquals(user, report.getUser());
        Assert.assertEquals(2, report.getStatus().size());
    }
}
