package org.as.devtechsolution.accounts.mapper;

import org.as.devtechsolution.accounts.dto.CustomerDto;
import org.as.devtechsolution.accounts.entity.Customer;

/**
 * @author Aditya Srivastva on 04-02-2024
 */
public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
