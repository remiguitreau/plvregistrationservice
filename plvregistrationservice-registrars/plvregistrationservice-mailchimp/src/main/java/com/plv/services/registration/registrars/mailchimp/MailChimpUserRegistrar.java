package com.plv.services.registration.registrars.mailchimp;

import com.plv.services.registration.api.model.User;
import com.plv.services.registration.core.UserRegistrar;

public class MailChimpUserRegistrar implements UserRegistrar {

    @Override
    public void register(final User user) {

    }

    @Override
    public String getType() {
        return "mailchimp-newsletter";
    }

}
