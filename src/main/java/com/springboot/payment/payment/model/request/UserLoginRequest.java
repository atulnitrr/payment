package com.springboot.payment.payment.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserLoginRequest {

    @Email(message = "Invalid email")
    @NotNull(message = "email can not be empty")
    private String email;

    @NotNull
    @Size(min = 3, max = 13, message = "password can be between 3 and 13 char")
    private String password;

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
