package com.sleepy.eebankmanagement.servlet;

import com.sleepy.eebankmanagement.model.dto.CustomerDTO;
import com.sleepy.eebankmanagement.services.AuthService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/auth")
@Slf4j
public class AuthServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect("/auth");
            return;
        }

        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
        JakartaServletWebApplication application = (JakartaServletWebApplication) servletContext.getAttribute("application");

        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext ctx = new WebContext(exchange, req.getLocale());

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("auth", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
        JakartaServletWebApplication application = (JakartaServletWebApplication) servletContext.getAttribute("application");

        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext ctx = new WebContext(exchange, req.getLocale());

        if ("login".equals(action)) {
            handleLogin(req, resp, ctx);
        } else if ("signup".equals(action)) {
            handleSignup(req, resp, ctx);
        }

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("auth", ctx, resp.getWriter());
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp, WebContext ctx) {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            CustomerDTO customer = authService.customerLogin(email, password);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getFirstName() + " " + customer.getLastName());

            resp.sendRedirect("/dashboard");

        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            ctx.setVariable("loginError", true);
            ctx.setVariable("errorMessage", e.getMessage());
        }
    }

    private void handleSignup(HttpServletRequest req, HttpServletResponse resp, WebContext ctx) {
        try {
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String nationalId = req.getParameter("nationalId");
            String email = req.getParameter("email");
            String phoneNumber = req.getParameter("phoneNumber");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            String address = req.getParameter("address");

            if (!password.equals(confirmPassword)) {
                throw new RuntimeException("رمز عبور و تکرار آن مطابقت ندارند");
            }

            CustomerDTO customer = authService.signup(firstName, lastName, nationalId,
                    email, phoneNumber, password, address);

            ctx.setVariable("signupSuccess", true);
            ctx.setVariable("successMessage", "ثبت‌نام با موفقیت انجام شد! اکنون می‌توانید وارد شوید.");

        } catch (Exception e) {
            log.error("Signup error: {}", e.getMessage());
            ctx.setVariable("signupError", true);
            ctx.setVariable("errorMessage", e.getMessage());
        }
    }
}