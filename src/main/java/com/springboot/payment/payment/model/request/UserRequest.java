package com.springboot.payment.payment.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserRequest {

    @NotNull(message = "name can not be null")
    @Size(min = 2, max = 88, message = "name should  be between 3 and 88 char")
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 3, max = 15, message = "password should be between 3 and 15 char")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
