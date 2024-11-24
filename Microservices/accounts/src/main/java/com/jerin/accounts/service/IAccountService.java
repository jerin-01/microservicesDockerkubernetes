package com.jerin.accounts.service;

import com.jerin.accounts.dto.AccountDTO;
import com.jerin.accounts.dto.CustomerDto;
import com.jerin.accounts.entity.Customer;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;


public interface IAccountService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount( String mobileNumber);
}
