package com.plv.services.registration.core;

import com.plv.services.registration.api.model.User;

public interface UserRegistrar {

    void register(User user);

    String getType();

}