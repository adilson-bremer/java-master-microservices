package com.study.accounts.services.impl;

import com.study.accounts.dtos.*;
import com.study.accounts.entities.Account;
import com.study.accounts.entities.Customer;
import com.study.accounts.exceptions.ResourceNotFoundException;
import com.study.accounts.mappers.AccountMapper;
import com.study.accounts.mappers.CustomerMapper;
import com.study.accounts.repositories.AccountRepository;
import com.study.accounts.repositories.CustomerRepository;
import com.study.accounts.services.ICustomerService;
import com.study.accounts.services.client.CardsFeignClient;
import com.study.accounts.services.client.LoansFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @Autowired
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);

        if (loanDtoResponseEntity != null) {
            customerDto.setLoanDto(loanDtoResponseEntity.getBody());
        }

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);

        if (cardDtoResponseEntity != null) {
            customerDto.setCardDto(cardDtoResponseEntity.getBody());
        }

        return customerDto;
    }
}
