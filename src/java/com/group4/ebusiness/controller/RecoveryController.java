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
public class RecoveryController {

    @EJB
    private UserEJB userEJB;
    
    @EJB
    private EmailService emailService;

    private String email;
    private String recoveryCode;
    private String newPassword;

    public String sendRecoveryCode() {

        User user = userEJB.createRecoveryCode(email);

        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Recovery failed",
                            "No account was found with that email."
                    )
            );

            return null;
        }

        emailService.sendEmail(
                user.getEmail(),
                "Group 4 e-Business Account Recovery",
                "Hello " + user.getFirstName()
                + ",\n\nYour recovery code is: "
                + user.getRecoveryCode()
                + "\n\nPlease use this code to reset your password."
        );

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("recoveryEmail", user.getEmail());

        return "resetPassword?faces-redirect=true";
    }

    public String resetPassword() {

        FacesContext context = FacesContext.getCurrentInstance();

        String savedEmail = (String) context
                .getExternalContext()
                .getSessionMap()
                .get("recoveryEmail");

        if (savedEmail == null) {
            return null;
        }

        boolean success = userEJB.resetPassword(
                savedEmail,
                recoveryCode,
                newPassword
        );

        if (success) {

            context.getExternalContext()
                    .getSessionMap()
                    .remove("recoveryEmail");

            return "login?faces-redirect=true";
        }

        context.addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Reset failed",
                        "Invalid recovery code."
                )
        );

        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}