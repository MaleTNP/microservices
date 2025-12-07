package com.thanapongBank.accounts.service;

import com.thanapongBank.accounts.dto.CustomerDetailsDto;
import com.thanapongBank.accounts.dto.CustomerDto;

public interface ICustomersService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
