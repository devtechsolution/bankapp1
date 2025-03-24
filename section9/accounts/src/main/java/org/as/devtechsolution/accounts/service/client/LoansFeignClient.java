package org.as.devtechsolution.accounts.service.client;

import jakarta.validation.constraints.Pattern;
import org.as.devtechsolution.accounts.dto.CardsDto;
import org.as.devtechsolution.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Aditya Srivastva
 */

@FeignClient("loans")
public interface LoansFeignClient {


    /**
     * Fetch card details based on a mobile number.
     *
     * @param mobileNumber the mobile number used to fetch card details
     * @return a ResponseEntity containing the card details wrapped in a CardsDto
     */
    @GetMapping(value = "/api/loan", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);

}
