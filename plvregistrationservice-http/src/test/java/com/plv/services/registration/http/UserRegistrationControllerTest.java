package com.plv.services.registration.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plv.services.registration.api.contracts.UserRegistrationService;
import com.plv.services.registration.api.model.ProcessState;
import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;

import java.io.UnsupportedEncodingException;

public class UserRegistrationControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @Mock
    private UserRegistrationService userRegistrationService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
    }

    @Test
    public void registration_proceed_and_no_registration_to_newsletter_asked()
            throws UnsupportedEncodingException, Exception {
        final User user = User.builder().email("test@domain.com").firstname("Henri").name("Newton").mobile(
                "mobile").zipcode("05000").build();
        final UserRegistrationReport report = UserRegistrationReport.newUserRegistrationReport(
                user).withRegistrationStatus("reg1", ProcessState.FAILED).withRegistrationStatus("reg2",
                        ProcessState.SUCCESS);
        Mockito.when(userRegistrationService.registerNewUser(user, true)).thenReturn(report);

        final String reportJson = mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/registration?newsletter=true").content(jsonMapper.writeValueAsString(user)).contentType(
                        MediaType.APPLICATION_JSON_UTF8)).andExpect(
                                MockMvcResultMatchers.status().isOk()).andExpect(
                                        MockMvcResultMatchers.content().contentType(
                                                MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString();

        Assert.assertEquals(report, jsonMapper.readValue(reportJson, UserRegistrationReport.class));
    }

}
