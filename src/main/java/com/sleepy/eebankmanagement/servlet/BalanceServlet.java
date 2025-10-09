package com.sleepy.eebankmanagement.servlet;

import com.sleepy.eebankmanagement.services.TransferService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/balance")
public class BalanceServlet extends HttpServlet {

    @Inject
    private TransferService service;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String cardNumber = req.getParameter("card");
            var balance = service.getBalance(cardNumber);

            out.println("{\"success\": true, \"balance\": " + balance + "}");
        } catch (Exception e) {
            out.println("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}