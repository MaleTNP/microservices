package com.thanapongBank.accounts.service.impl;

import com.thanapongBank.accounts.dto.AccountsDto;
import com.thanapongBank.accounts.dto.CardsDto;
import com.thanapongBank.accounts.dto.CustomerDetailsDto;
import com.thanapongBank.accounts.dto.LoansDto;
import com.thanapongBank.accounts.entity.Accounts;
import com.thanapongBank.accounts.entity.Customer;
import com.thanapongBank.accounts.exception.ResourceNotFoundException;
import com.thanapongBank.accounts.mapper.AccountsMapper;
import com.thanapongBank.accounts.mapper.CustomerMapper;
import com.thanapongBank.accounts.repository.AccountsRepository;
import com.thanapongBank.accounts.repository.CustomerRepository;
import com.thanapongBank.accounts.service.ICustomersService;
import com.thanapongBank.accounts.service.client.CardsFeignClient;
import com.thanapongBank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoan(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCard(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
