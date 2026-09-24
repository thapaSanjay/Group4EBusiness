package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.UserEJB;
import com.group4.ebusiness.entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Handles user login and logout operations.
 * A successfully authenticated user is stored in the HTTP session.
 */
@Named
@RequestScoped
public class LoginController {

    @EJB
    private UserEJB userEJB;

    private String username;
    private String password;

    /**
    * Authenticates the supplied username and password.
    * On success, stores the logged-in user in the session
    * and redirects to the main dashboard.
    */
    public String login() {

        User user = userEJB.login(username, password);

        if (user != null) {

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("loggedInUser", user);

            return "index?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Invalid username/password or account not verified.",
                        null
                )
        );

        return null;
    }

    /**
    * Logs out current user by invalidating the HTTP session.
    */
    public String logout() {

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "login?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}