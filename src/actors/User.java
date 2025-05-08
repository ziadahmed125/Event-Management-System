package actors;

import core.Utility;
import interfaces.*;
import models.Gender;

import java.time.LocalDate;
import java.util.Objects;

public abstract class User {

    protected String firstName;
    protected String lastName;
    protected String username;
    protected Gender gender;
    protected LocalDate dateOfBirth;
    protected String address;
    protected String email;
    protected String password;


    public User() {
    }

    public User(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + ' ' + lastName
                + "\nUsername: " + username
                + "\nGender: " + gender
                + "\nDate Of Birth: " +dateOfBirth
                + "\nAddress: " + address
                + "\nEmail: " + email;
    }

    public void displayProfile(){
        System.out.println(
                "Name: " + firstName + " " + lastName
                + "\nUsername: " + username
                + "\nGender: " + gender
                + "\nDate Of Birth: " +dateOfBirth
                + "\nAddress: " + address
                + "\nEmail: " + email
        );
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
