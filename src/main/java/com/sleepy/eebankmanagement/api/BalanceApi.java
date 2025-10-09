package com.sleepy.eebankmanagement.api;

import com.sleepy.eebankmanagement.dto.ApiResponse;
import com.sleepy.eebankmanagement.services.TransferService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Path("/api/balance")
@Produces(MediaType.APPLICATION_JSON)
public class BalanceApi {

    @Inject
    private TransferService transferService;

    @GET
    @Path("/{cardNumber}")
    public Response getBalance(@PathParam("cardNumber") String cardNumber) {
        ApiResponse<BalanceResult> apiResponse = new ApiResponse<>();

        try {
            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                apiResponse.setStatus(400);
                apiResponse.setMessage("Card number is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(apiResponse).build();
            }

            BigDecimal balance = transferService.getBalance(cardNumber);

            BalanceResult balanceResult = new BalanceResult(cardNumber, balance, "USD");

            apiResponse.setStatus(200);
            apiResponse.setMessage("Balance retrieved successfully");
            apiResponse.setData(balanceResult);

            return Response.status(Response.Status.OK).entity(apiResponse).build();

        } catch (Exception e) {
            apiResponse.setStatus(404);
            apiResponse.setMessage("Card not found: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(apiResponse).build();
        }
    }

    // Inner Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BalanceResult {
        private String cardNumber;
        private BigDecimal balance;
        private String currency;
    }
}