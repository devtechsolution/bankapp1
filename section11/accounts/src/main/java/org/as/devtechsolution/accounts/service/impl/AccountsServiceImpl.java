package org.as.devtechsolution.accounts.service.impl;

import lombok.AllArgsConstructor;
import org.as.devtechsolution.accounts.constant.AccountsConstants;
import org.as.devtechsolution.accounts.dto.AccountsDto;
import org.as.devtechsolution.accounts.dto.CustomerDto;
import org.as.devtechsolution.accounts.entity.Accounts;
import org.as.devtechsolution.accounts.entity.Customer;
import org.as.devtechsolution.accounts.exception.CustomerAlreadyExistsException;
import org.as.devtechsolution.accounts.exception.ResourceNotFoundException;
import org.as.devtechsolution.accounts.mapper.AccountMapper;
import org.as.devtechsolution.accounts.mapper.CustomerMapper;
import org.as.devtechsolution.accounts.repository.AccountsRepository;
import org.as.devtechsolution.accounts.repository.CustomerRepository;
import org.as.devtechsolution.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author Aditya Srivastva on 04-02-2024
 */

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(final CustomerDto customerDto) {

        final var byMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
        /*customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Aditya");*/
        final var savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));



    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        /*newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Aditya");*/
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(final CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(final String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
