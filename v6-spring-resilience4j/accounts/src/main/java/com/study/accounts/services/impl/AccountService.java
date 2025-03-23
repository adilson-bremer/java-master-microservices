package com.study.accounts.services.impl;

import com.study.accounts.constants.AccountsConstants;
import com.study.accounts.dtos.AccountDto;
import com.study.accounts.dtos.CustomerDto;
import com.study.accounts.entities.Account;
import com.study.accounts.entities.Customer;
import com.study.accounts.exceptions.CustomerAlreadyExistsException;
import com.study.accounts.exceptions.ResourceNotFoundException;
import com.study.accounts.mappers.AccountMapper;
import com.study.accounts.mappers.CustomerMapper;
import com.study.accounts.repositories.AccountRepository;
import com.study.accounts.repositories.CustomerRepository;
import com.study.accounts.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper
                .mapToCustomerEntity(customerDto, new Customer());

        Optional<Customer> optionalCustomer =
                customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(
                    "Customer already registered with mobile number " + customerDto.getMobileNumber());
        }

        Customer customerCreated = customerRepository.save(customer);

        accountRepository.save(createNewAccount(customerCreated));
    }

    private Account createNewAccount(Customer customer) {

        Account account = new Account();

        account.setCustomerId(customer.getCustomerId());

        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);

        return account;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountDto accountDto = customerDto.getAccountDto();

        if (accountDto != null) {
            Account accountEntity = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Account", "accountNumber", accountDto.getAccountNumber().toString()));

            AccountMapper.mapToAccountEntity(accountDto, accountEntity);

            accountEntity = accountRepository.save(accountEntity);

            Long customerId = accountEntity.getCustomerId();

            Customer customerEntity = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

            CustomerMapper.mapToCustomerEntity(customerDto, customerEntity);

            customerRepository.save(customerEntity);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
