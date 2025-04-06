package org.as.devtechsolution.accounts.service;

import org.as.devtechsolution.accounts.dto.CustomerDetailsDto;

/**
 * @author Aditya Srivastva
 */
public interface ICustomersService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
