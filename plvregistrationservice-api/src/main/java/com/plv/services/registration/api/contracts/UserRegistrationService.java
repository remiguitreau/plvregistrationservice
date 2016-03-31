package com.plv.services.registration.api.contracts;

import com.plv.services.registration.api.model.User;
import com.plv.services.registration.api.model.UserRegistrationReport;

public interface UserRegistrationService {

    UserRegistrationReport registerNewUser(final User user, final boolean registerOnNewsLetter);
}
