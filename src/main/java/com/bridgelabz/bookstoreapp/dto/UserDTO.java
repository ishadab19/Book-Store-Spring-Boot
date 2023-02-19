package com.bridgelabz.bookstoreapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public @ToString class UserDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First name is Invalid")
    @NotEmpty(message = "First name cannot be null")
    public String firstName;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Last name is Invalid")
    @NotEmpty(message = "Last name cannot be null")
    public String lastName;

    @NotEmpty(message = "Address cannot be null")
    public String address;


    public LocalDate dateOfBirth;

    @Pattern(regexp = "^[91]+[ ]?[6-9]{1}[0-9]{9}$", message = "phonenumber is invalid")
    @NotEmpty(message = "Phone number cannot be null")
    public String phoneNumber;

    @Pattern(regexp = "^[a-z]+[a-z0-9+_.-]*[@][a-z0-9]+[.][a-z]{2,4}[.]*([a-z]{2,3})*$", message = "email is invalid")
    @NotEmpty(message = "email should not be empty")
    public String email;

    @Pattern(regexp = "^[0-9a-zA-Z!,@#$&*().]{8,}$", message = "Password is Invalid")
    @NotEmpty(message = "Password cannot be null")
    public String password;

    public UserDTO(String firstName, String lastName, String address, LocalDate dateOfBirth,
                   String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }
}
