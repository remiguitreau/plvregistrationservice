package com.plv.services.registration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plv.services.registration.api.model.ProcessState;
import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;
import com.plv.services.registration.api.model.UserRegistrationStatus;
import com.plv.services.registration.core.UserRegistrar;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PLVUserRegistrationServiceApplication.class)
@WebAppConfiguration
public class PLVUserRegistrationServiceApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void init_test() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test_application_is_loadable() throws Exception {
        final User user = User.builder().email("user@domain.com").firstname("Bob").name("Hogan").mobile(
                "mobile").zipcode("05000").build();

        final UserRegistrationReport report = mapper.readValue(
                mockMvc.perform(MockMvcRequestBuilders.post("/api/registration?newsletter=false").content(
                        mapper.writeValueAsString(user)).contentType(
                                MediaType.APPLICATION_JSON_UTF8)).andExpect(
                                        MockMvcResultMatchers.status().isOk()).andExpect(
                                                MockMvcResultMatchers.content().contentType(
                                                        MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse().getContentAsString(),
                UserRegistrationReport.class);

        Assert.assertEquals(user, report.getUser());
        Assert.assertEquals(Arrays.asList(
                UserRegistrationStatus.builder().registrationType("registrar1").processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType("registrar2").processState(
                        ProcessState.SUCCESS).build(),
                UserRegistrationStatus.builder().registrationType("mailchimp-newsletter").processState(
                        ProcessState.SKIPPED).build()),
                report.getStatus());

        Mockito.verify((UserRegistrar) context.getBean("registrar1")).register(user);
        Mockito.verify((UserRegistrar) context.getBean("registrar2")).register(user);
    }
}
