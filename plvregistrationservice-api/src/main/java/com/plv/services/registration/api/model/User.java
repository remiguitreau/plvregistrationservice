package com.plv.services.registration.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;

    private String firstname;

    private String email;

    private String mobile;

    private String zipcode;
}
