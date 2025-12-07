package com.thanapongBank.loans.service.impl;

import com.thanapongBank.loans.constants.LoansConstants;
import com.thanapongBank.loans.dto.LoansDto;
import com.thanapongBank.loans.entity.Loans;
import com.thanapongBank.loans.exception.LoanAlreadyExistException;
import com.thanapongBank.loans.exception.ResourceNotFoundException;
import com.thanapongBank.loans.mapper.LoansMapper;
import com.thanapongBank.loans.repository.LoanRepository;
import com.thanapongBank.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoanService {

    private LoanRepository loansRepository;

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistException("Loan already registered with given mobileNumber");
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
//        newLoan.setCreatedAt(LocalDateTime.now());
//        newLoan.setCreatedBy("Anonymous");
        return newLoan;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Loan details based on given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     *
     * @param loansDto - LoanDto Object
     * @return boolean indicating if the update of loan details successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber())
                );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
