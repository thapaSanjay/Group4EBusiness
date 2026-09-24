package com.group4.ebusiness.security;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet filter protecting authenticated areas of the application.
 * Requests to protected JSF pages are redirected to login
 * when no logged-in user exists in the session.
 */
@WebFilter(urlPatterns = {
    "/index.xhtml",
    "/products.xhtml",
    "/customers.xhtml",
    "/orders.xhtml",
    "/search.xhtml",
    "/customerDetails.xhtml"
})
public class AuthenticationFilter implements Filter {

    /**
    * Initialises the authentication filter.
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
    * Checks whether the current session contains an authenticated user.
    * Authenticated requests continue normally; otherwise the user is
    * redirected to the login page.
    */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
                         throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        boolean loggedIn =
                session != null
                && session.getAttribute("loggedInUser") != null;

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/login.xhtml"
            );
        }
    }

    /**
    * Called by the servlet container when the filter is removed.
    */
    @Override
    public void destroy() {
    }
}