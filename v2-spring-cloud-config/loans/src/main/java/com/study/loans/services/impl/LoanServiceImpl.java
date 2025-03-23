package com.study.loans.services.impl;

import com.study.loans.constants.LoansConstants;
import com.study.loans.dtos.LoanDto;
import com.study.loans.entities.Loan;
import com.study.loans.exceptions.LoanAlreadyExistsException;
import com.study.loans.exceptions.ResourceNotFoundException;
import com.study.loans.mappers.LoanMapper;
import com.study.loans.repositories.LoanRepository;
import com.study.loans.services.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private LoanRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loan> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);

        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }

        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber) {

        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);

        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {

        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoanMapper.mapToLoansDto(loan, new LoanDto());
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {

        Loan loan = loansRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));

        LoanMapper.mapToLoans(loanDto, loan);

        loansRepository.save(loan);

        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        loansRepository.deleteById(loan.getLoanId());

        return true;
    }
}
