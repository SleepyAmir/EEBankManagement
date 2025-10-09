package com.sleepy.eebankmanagement.servlet;

import com.sleepy.eebankmanagement.services.TransferService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    @Inject
    private TransferService service;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            String result = service.transfer(from, to, amount);

            out.println("{\"success\": true, \"message\": \"" + result + "\"}");
        } catch (Exception e) {
            out.println("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}