package org.as.devtechsolution.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.as.devtechsolution.accounts.dto.CustomerDetailsDto;
import org.as.devtechsolution.accounts.dto.ErrorResponseDto;
import org.as.devtechsolution.accounts.service.ICustomersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aditya Srivastva
 */

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
@Validated
@Tag(
        name = "REST API for Customers in in Bankapp",
        description = "REST APIs in Bankapp to FETCH customer details"
)
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomersService customersService;

    public CustomerController(ICustomersService customersService){
        this.customersService = customersService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/customerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("bankapp-correlation-id")
            String correlationId,
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
           String mobileNumber){
        logger.debug("fetchCustomerDetails() method started");
        CustomerDetailsDto customerDetailsDto = customersService
                .fetchCustomerDetails(mobileNumber,correlationId);
        logger.debug("fetchCustomerDetails() method ended");
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

    }
}
