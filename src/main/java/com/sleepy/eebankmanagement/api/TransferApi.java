package com.sleepy.eebankmanagement.api;

import com.sleepy.eebankmanagement.model.entity.config.ApiResponse;
import com.sleepy.eebankmanagement.model.entity.transaction.TransferRequest;
import com.sleepy.eebankmanagement.services.TransferService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferApi {

    @Inject
    private TransferService transferService;

    @POST
    public Response transfer(TransferRequest request) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            String result = transferService.transfer(
                    request.getFromCard(),
                    request.getToCard(),
                    request.getAmount()
            );

            apiResponse.setStatus(200);
            apiResponse.setMessage("Transfer successful");
            apiResponse.setData(result);

            return Response.status(Response.Status.OK).entity(apiResponse).build();

        } catch (Exception e) {
            apiResponse.setStatus(500);
            apiResponse.setMessage("Transfer Error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponse).build();
        }
    }

    @GET
    @Path("/balance/{cardNumber}")
    public Response getBalance(@PathParam("cardNumber") String cardNumber) {
        ApiResponse apiResponse = new ApiResponse();

        try {
            BigDecimal balance = transferService.getBalance(cardNumber);

            apiResponse.setStatus(200);
            apiResponse.setMessage("Balance retrieved successfully");
            apiResponse.setData(balance);

            return Response.status(Response.Status.OK).entity(apiResponse).build();

        } catch (Exception e) {
            apiResponse.setStatus(404);
            apiResponse.setMessage("Card not found: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(apiResponse).build();
        }
    }


}