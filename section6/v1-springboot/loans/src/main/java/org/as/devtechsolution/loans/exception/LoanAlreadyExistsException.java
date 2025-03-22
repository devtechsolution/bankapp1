package org.as.devtechsolution.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Aditya Srivastva on 23-03-2024
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoanAlreadyExistsException extends RuntimeException {

    public LoanAlreadyExistsException(String message){
        super(message);
    }

}