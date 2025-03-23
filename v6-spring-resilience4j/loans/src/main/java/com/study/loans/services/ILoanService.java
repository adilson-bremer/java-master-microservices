package com.study.loans.services;

import com.study.loans.dtos.LoanDto;

public interface ILoanService {

    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto loanDto);

    boolean deleteLoan(String mobileNumber);
}
