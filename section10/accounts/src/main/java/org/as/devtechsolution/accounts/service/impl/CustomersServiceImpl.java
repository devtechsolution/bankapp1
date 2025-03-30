package org.as.devtechsolution.accounts.service.impl;

import lombok.AllArgsConstructor;
import org.as.devtechsolution.accounts.dto.AccountsDto;
import org.as.devtechsolution.accounts.dto.CardsDto;
import org.as.devtechsolution.accounts.dto.CustomerDetailsDto;
import org.as.devtechsolution.accounts.dto.LoansDto;
import org.as.devtechsolution.accounts.entity.Accounts;
import org.as.devtechsolution.accounts.entity.Customer;
import org.as.devtechsolution.accounts.exception.ResourceNotFoundException;
import org.as.devtechsolution.accounts.mapper.AccountMapper;
import org.as.devtechsolution.accounts.mapper.CustomerMapper;
import org.as.devtechsolution.accounts.repository.AccountsRepository;
import org.as.devtechsolution.accounts.repository.CustomerRepository;
import org.as.devtechsolution.accounts.service.ICustomersService;
import org.as.devtechsolution.accounts.service.client.CardsFeignClient;
import org.as.devtechsolution.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Aditya Srivastva
 */

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    /**
     * Fetch customer details based on a given mobile number.
     *
     * @param mobileNumber Mobile number of the customer
     * @return Customer details
     */

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loansDtoResponseEntity!=null) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);

        if (cardsDtoResponseEntity!=null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
