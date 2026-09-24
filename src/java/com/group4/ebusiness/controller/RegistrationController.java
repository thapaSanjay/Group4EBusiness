package com.group4.ebusiness.controller;

import jakarta.ejb.EJBException;
import com.group4.ebusiness.ejb.EmailService;
import com.group4.ebusiness.ejb.UserEJB;
import com.group4.ebusiness.entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Handles new user registration.
 * Registered users receive an email verification code before login is permitted.
 */
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

    /**
    * Registers a new user, sends the verification code by email
    * and redirects the user to the verification page.
    */
    public String register() {

        try {
            User user = userEJB.registerUser(
                    firstName,
                    lastName,
                    username,
                    email,
                    password
            );
            // Send the generated verification code to the user's registered email address.
            emailService.sendEmail(
                user.getEmail(),
                "Group 4 e-Business Registration Verification",
                "Hello " + user.getFirstName()
                + ",\n\nYour verification code is: "
                + user.getVerificationCode()
                + "\n\nPlease use this code to complete your registration."
            );
            // Store the registration email temporarily for the verification step.
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("registrationEmail", user.getEmail());

            return "verify?faces-redirect=true";

       }
        // Convert EJB validation errors into user-friendly JSF messages.
        catch (EJBException e) {

            Throwable cause = e.getCause();

            String message = "Registration failed.";

            if (cause instanceof IllegalArgumentException) {
                message = cause.getMessage();
            }

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            message,
                            null
                    )
            );

            return null;

        } catch (IllegalArgumentException e) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            e.getMessage(),
                            null
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