package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.EmailService;
import com.group4.ebusiness.ejb.UserEJB;
import com.group4.ebusiness.entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class RegistrationController {

    @EJB
    private UserEJB userEJB;
    
    @EJB
    private EmailService emailService;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public String register() {

        try {
            User user = userEJB.registerUser(
                    firstName,
                    lastName,
                    username,
                    email,
                    password
            );
            
            emailService.sendEmail(
                user.getEmail(),
                "Group 4 e-Business Registration Verification",
                "Hello " + user.getFirstName()
                + ",\n\nYour verification code is: "
                + user.getVerificationCode()
                + "\n\nPlease use this code to complete your registration."
            );

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("registrationEmail", user.getEmail());

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();

            return "verify?faces-redirect=true";

        } catch (IllegalArgumentException e) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Registration failed",
                            e.getMessage()
                    )
            );

            return null;
        }
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
}