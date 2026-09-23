package com.group4.ebusiness.controller;

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

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("recoveryEmail", user.getEmail());

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("recoveryCode", user.getRecoveryCode());

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

            context.getExternalContext()
                    .getSessionMap()
                    .remove("recoveryCode");

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