package com.sleepy.eebankmanagement.servlet;

import com.sleepy.eebankmanagement.model.dto.CardDTO;
import com.sleepy.eebankmanagement.model.dto.CustomerDTO;
import com.sleepy.eebankmanagement.model.entity.card.Card;
import com.sleepy.eebankmanagement.services.CardInfoService;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/dashboard")
@Slf4j
public class DashboardServlet extends HttpServlet {

    @Inject
    private CardInfoService cardInfoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/auth");
            return;
        }

        CustomerDTO customer = (CustomerDTO) session.getAttribute("customer");
        if (customer == null) {
            resp.sendRedirect("/auth");
            return;
        }

        ServletContext servletContext = getServletContext();
        TemplateEngine templateEngine = (TemplateEngine) servletContext.getAttribute("templateEngine");
        JakartaServletWebApplication application = (JakartaServletWebApplication) servletContext.getAttribute("application");

        IWebExchange exchange = application.buildExchange(req, resp);
        WebContext ctx = new WebContext(exchange, req.getLocale());

        try {
            // Get cards and convert to DTOs
            List<Card> cardEntities = cardInfoService.getCustomerCards(customer.getId());
            List<CardDTO> cards = cardEntities.stream()
                    .map(CardDTO::new)
                    .collect(Collectors.toList());

            ctx.setVariable("customerName", customer.getFullName());
            ctx.setVariable("customerEmail", customer.getEmail());
            ctx.setVariable("cards", cards);
            ctx.setVariable("hasCards", !cards.isEmpty());

        } catch (Exception e) {
            log.error("Error loading dashboard: {}", e.getMessage());
            ctx.setVariable("error", "خطا در بارگذاری اطلاعات");
        }

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("dashboard", ctx, resp.getWriter());
    }
}