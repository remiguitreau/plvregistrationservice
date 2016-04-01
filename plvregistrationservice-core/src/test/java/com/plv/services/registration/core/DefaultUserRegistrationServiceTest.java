package com.plv.services.registration.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.plv.services.registration.api.model.ProcessState;
import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;
import com.plv.services.registration.api.model.UserRegistrationStatus;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRegistrationServiceTest {

    private static final String NEWSLETTER_REGISTRAR_TYPE = "NLRegistrar";

    private static final String REGISTRAR_TYPE_1 = "REG1";

    private static final String REGISTRAR_TYPE_2 = "REG2";

    private DefaultUserRegistrationService userRegistrationService;

    @Mock
    private UserRegistrar newsLetterRegistrar;

    @Mock
    private UserRegistrar registrar1;

    @Mock
    private UserRegistrar registrar2;

    @Before
    public void init_test() {
        Mockito.when(newsLetterRegistrar.getType()).thenReturn(NEWSLETTER_REGISTRAR_TYPE);
        Mockito.when(registrar1.getType()).thenReturn(REGISTRAR_TYPE_1);
        Mockito.when(registrar2.getType()).thenReturn(REGISTRAR_TYPE_2);
        userRegistrationService = new DefaultUserRegistrationService(newsLetterRegistrar);
        userRegistrationService.register(registrar1);
        userRegistrationService.register(registrar2);
    }

    @Test
    public void test_all_registration_ok_but_no_newsletter_registration() {
        final User user = Mockito.mock(User.class);

        final UserRegistrationReport report = userRegistrationService.registerNewUser(user, false);

        Assert.assertEquals(user, report.getUser());
        Assert.assertEquals(Arrays.asList(
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_1).processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_2).processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType(NEWSLETTER_REGISTRAR_TYPE).processState(
                        ProcessState.SKIPPED).build()),
                report.getStatus());
    }

    @Test
    public void test_all_registration_ok() {
        final User user = Mockito.mock(User.class);

        final UserRegistrationReport report = userRegistrationService.registerNewUser(user, true);

        Assert.assertEquals(user, report.getUser());
        Assert.assertEquals(Arrays.asList(
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_1).processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_2).processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType(NEWSLETTER_REGISTRAR_TYPE).processState(
                        ProcessState.SUCCESS).build()),
                report.getStatus());
    }

    @Test
    public void test_newsletter_registration_failed_and_first_registrar_also() {
        final User user = Mockito.mock(User.class);
        Mockito.doThrow(UserRegistrationException.class).when(newsLetterRegistrar).register(user);
        Mockito.doThrow(UserRegistrationException.class).when(registrar1).register(user);

        final UserRegistrationReport report = userRegistrationService.registerNewUser(user, true);

        Assert.assertEquals(user, report.getUser());
        Assert.assertEquals(Arrays.asList(
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_1).processState(
                        ProcessState.FAILED).build(),
                UserRegistrationStatus.builder().registrationType(REGISTRAR_TYPE_2).processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType(NEWSLETTER_REGISTRAR_TYPE).processState(
                        ProcessState.FAILED).build()),
                report.getStatus());
    }

}
