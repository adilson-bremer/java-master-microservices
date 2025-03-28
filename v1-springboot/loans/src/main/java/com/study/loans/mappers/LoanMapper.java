package com.study.loans.mappers;

import com.study.loans.dtos.LoanDto;
import com.study.loans.entities.Loan;

public class LoanMapper {

    public static LoanDto mapToLoansDto(Loan loan, LoanDto loanDto) {

        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());

        return loanDto;
    }

    public static Loan mapToLoans(LoanDto loanDto, Loan loan) {

        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());

        return loan;
    }
}
