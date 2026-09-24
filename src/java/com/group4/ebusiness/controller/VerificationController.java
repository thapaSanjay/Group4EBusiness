package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.UserEJB;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Handles verification of newly registered user accounts.
 */
@Named
@RequestScoped
public class VerificationController {

    @EJB
    private UserEJB userEJB;

    private String code;

    /**
    * Checks the verification code against the code stored for
    * the registered account and activates the account when valid.
    */
    public String verify() {

        FacesContext context = FacesContext.getCurrentInstance();

        String email = (String) context
                .getExternalContext()
                .getSessionMap()
                .get("registrationEmail");

        if (email == null) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Verification failed",
                            "Registration session not found."
                    )
            );

            return null;
        }

        boolean verified = userEJB.verifyUser(email, code);

        // Registration information is no longer required after successful verification.
        if (verified) {

            context.getExternalContext()
                    .getSessionMap()
                    .remove("registrationEmail");

            context.getExternalContext()
                    .getSessionMap()
                    .remove("verificationCode");

            return "login?faces-redirect=true";
        }

        context.addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Verification failed",
                        "Invalid verification code."
                )
        );

        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}