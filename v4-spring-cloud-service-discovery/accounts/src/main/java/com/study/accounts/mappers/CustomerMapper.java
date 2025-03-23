package com.study.accounts.mappers;

import com.study.accounts.dtos.CustomerDetailsDto;
import com.study.accounts.dtos.CustomerDto;
import com.study.accounts.entities.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customerEntity, CustomerDto customerDto) {

        customerDto.setName(customerEntity.getName());
        customerDto.setEmail(customerEntity.getEmail());
        customerDto.setMobileNumber(customerEntity.getMobileNumber());

        return customerDto;
    }

    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customerEntity, CustomerDetailsDto customerDto) {

        customerDto.setName(customerEntity.getName());
        customerDto.setEmail(customerEntity.getEmail());
        customerDto.setMobileNumber(customerEntity.getMobileNumber());

        return customerDto;
    }

    public static Customer mapToCustomerEntity(CustomerDto customerDto, Customer customerEntity) {

        customerEntity.setName(customerDto.getName());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setMobileNumber(customerDto.getMobileNumber());

        return customerEntity;
    }
}
