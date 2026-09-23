package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.User;
import com.group4.ebusiness.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.security.SecureRandom;

@Stateless
public class UserEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    private static final SecureRandom RANDOM = new SecureRandom();

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

    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

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

    public User createRecoveryCode(String email) {

        User user = findByEmail(email);

        if (user == null) {
            return null;
        }

        user.setRecoveryCode(generateCode());

        em.merge(user);

        return user;
    }

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

    private String generateCode() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }
}