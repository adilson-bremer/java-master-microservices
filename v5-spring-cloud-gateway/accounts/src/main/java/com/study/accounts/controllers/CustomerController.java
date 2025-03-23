package com.study.accounts.controllers;

import com.study.accounts.dtos.CustomerDetailsDto;
import com.study.accounts.services.ICustomerService;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/fetch-customer-details")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("studybank-correlation-id") String correlationId,
            @RequestParam @Pattern(
                    regexp = "^$|[0-9]{11}",
                    message = "Mobile number must be 11 digits") String mobileNumber) {

        logger.debug("studybank-correlation-id found: {}", correlationId);

        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);

        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);
    }
}
