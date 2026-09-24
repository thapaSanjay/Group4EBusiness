package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.User;
import com.group4.ebusiness.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.security.SecureRandom;

/**
 * Stateless business component for user registration,
 * authentication, verification and account recovery.
 */
@Stateless
public class UserEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
    * Registers a new user after checking that the username and email
    * are unique. The password is stored as a SHA-512 hash and a
    * verification code is generated for account activation.
    */
    public User registerUser(String firstName,
                             String lastName,
                             String username,
                             String email,
                             String password) {

        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        String passwordHash = PasswordUtil.hashPassword(password);

        User user = new User(
                firstName,
                lastName,
                username,
                email,
                passwordHash
        );

        user.setVerificationCode(generateCode());

        em.persist(user);

        return user;
    }

    /**
    * Retrieves a user by username using the User.findByUsername named query.
    */
    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
    * Retrieves a user by registered email address.
    */
    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Verifies a user account when the supplied verification code matches.
     */
    public boolean verifyUser(String email, String code) {

        User user = findByEmail(email);

        if (user == null) {
            return false;
        }

        if (user.getVerificationCode() != null
                && user.getVerificationCode().equals(code)) {

            user.setVerified(true);
            user.setVerificationCode(null);

            em.merge(user);

            return true;
        }

        return false;
    }

    /**
    * Authenticates a verified account by comparing the hashed
    * supplied password with the stored password hash.
    */
    public User login(String username, String password) {

        User user = findByUsername(username);

        if (user == null || !user.isVerified()) {
            return null;
        }

        String enteredHash = PasswordUtil.hashPassword(password);

        if (enteredHash.equals(user.getPasswordHash())) {
            return user;
        }

        return null;
    }

    /**
    * Generates and stores a temporary recovery code for the
    * account associated with the supplied email address.
    */
    public User createRecoveryCode(String email) {

        User user = findByEmail(email);

        if (user == null) {
            return null;
        }

        user.setRecoveryCode(generateCode());

        em.merge(user);

        return user;
    }

    /**
    * Resets the user's password after validating the recovery code.
    * The new password is stored as a SHA-512 hash.
    */
    public boolean resetPassword(String email,
                                 String recoveryCode,
                                 String newPassword) {

        User user = findByEmail(email);

        if (user == null) {
            return false;
        }

        if (user.getRecoveryCode() != null
                && user.getRecoveryCode().equals(recoveryCode)) {

            user.setPasswordHash(
                    PasswordUtil.hashPassword(newPassword)
            );

            user.setRecoveryCode(null);

            em.merge(user);

            return true;
        }

        return false;
    }

    /**
    * Generates a random six-digit verification or recovery code.
    */
    private String generateCode() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }
}