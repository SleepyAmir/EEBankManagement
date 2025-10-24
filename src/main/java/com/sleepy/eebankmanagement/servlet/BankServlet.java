package com.sleepy.eebankmanagement.servlet;

import com.sleepy.eebankmanagement.model.entity.Customer;
import com.sleepy.eebankmanagement.services.TransferService;
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
import java.math.BigDecimal;

@WebServlet("/bank")
@Slf4j
public class BankServlet extends HttpServlet {

    @Inject
    private TransferService transferService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            resp.sendRedirect("/auth");
            return;
        }

        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
        JakartaServletWebApplication application = (JakartaServletWebApplication) servletContext.getAttribute("application");

        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext ctx = new WebContext(exchange, req.getLocale());

        // Add customer info to context
        ctx.setVariable("customerName", session.getAttribute("customerName"));

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("bank", ctx, resp.getWriter());
    }

    private void handleTransfer(HttpServletRequest req, WebContext ctx) {
        try {
            String fromCard = req.getParameter("fromCard");
            String toCard = req.getParameter("toCard");
            String amountStr = req.getParameter("amount");

            BigDecimal amount = new BigDecimal(amountStr);
            String result = transferService.transfer(fromCard, toCard, amount);

            ctx.setVariable("transferSuccess", true);
            ctx.setVariable("transferMessage", result);

        } catch (Exception e) {
            log.error("Transfer error: {}", e.getMessage());
            ctx.setVariable("transferSuccess", false);
            ctx.setVariable("transferMessage", e.getMessage());
        }
    }

    private void handleBalance(HttpServletRequest req, WebContext ctx) {
        try {
            String cardNumber = req.getParameter("cardNumber");
            BigDecimal balance = transferService.getBalance(cardNumber);

            ctx.setVariable("balanceSuccess", true);
            ctx.setVariable("balance", balance);
            ctx.setVariable("cardNumber", cardNumber);

        } catch (Exception e) {
            log.error("Balance check error: {}", e.getMessage());
            ctx.setVariable("balanceSuccess", false);
            ctx.setVariable("balanceMessage", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            resp.sendRedirect("/auth");
            return;
        }

        String action = req.getParameter("action");

        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
        JakartaServletWebApplication application = (JakartaServletWebApplication) servletContext.getAttribute("application");

        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext ctx = new WebContext(exchange, req.getLocale());

        // Add customer info to context
        ctx.setVariable("customerName", session.getAttribute("customerName"));

        if ("transfer".equals(action)) {
            handleTransfer(req, ctx);
        } else if ("balance".equals(action)) {
            handleBalance(req, ctx);
        }

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("bank", ctx, resp.getWriter());
    }
}
