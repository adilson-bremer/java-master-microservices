package com.study.accounts.services;

import com.study.accounts.dtos.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
