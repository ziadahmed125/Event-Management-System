package Interfaces;

import models.Gender;

import java.time.LocalDate;

public interface Iuser {
    // Basic User Information Methods
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
    String getUsername();
    void setUsername(String username);
    Gender getGender();
    void setGender(Gender gender);
    String getEmail();
    void setEmail(String email);
    String getPassword();
    void setPassword(String password);
    String getAddress();
    void setAddress(String address);
    LocalDate getDateOfBirth();
    void setDateOfBirth(LocalDate dateOfBirth);
}
