package com.plv.services.registration.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plv.services.registration.api.contracts.UserRegistrationService;
import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @RequestMapping(value = "/api/registration", method = RequestMethod.POST)
    @ResponseBody
    public UserRegistrationReport registerNewUser(@RequestBody
    final User user, @RequestParam("newsletter")
    final boolean registerOnNewsLetter) {
        return userRegistrationService.registerNewUser(user, registerOnNewsLetter);
    }
}
