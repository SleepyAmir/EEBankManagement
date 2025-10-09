package com.sleepy.eebankmanagement.api;

import com.sleepy.eebankmanagement.services.TransferService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/api/transfer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferApi {

    @Inject
    private TransferService service;

    @POST
    public Response transfer(TransferRequest request) {
        try {
            String result = service.transfer(
                    request.fromCard,
                    request.toCard,
                    request.amount
            );
            return Response.ok(new ApiResponse(200, result)).build();
        } catch (Exception e) {
            return Response.status(400)
                    .entity(new ApiResponse(400, e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/balance/{card}")
    public Response balance(@PathParam("card") String card) {
        try {
            BigDecimal balance = service.getBalance(card);
            return Response.ok(new ApiResponse(200, "Balance: " + balance)).build();
        } catch (Exception e) {
            return Response.status(404)
                    .entity(new ApiResponse(404, e.getMessage()))
                    .build();
        }
    }

    // Classes
    public static class TransferRequest {
        public String fromCard;
        public String toCard;
        public BigDecimal amount;
    }

    public static class ApiResponse {
        public int status;
        public String message;

        public ApiResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}