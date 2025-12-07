package com.thanapongBank.accounts.service.client;

import com.thanapongBank.accounts.dto.CardsDto;
import com.thanapongBank.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoan(
            @RequestHeader("malebank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);

}
