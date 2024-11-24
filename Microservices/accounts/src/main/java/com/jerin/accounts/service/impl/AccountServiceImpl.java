package com.jerin.accounts.service.impl;

import com.jerin.accounts.constants.AccountsConstants;
import com.jerin.accounts.dto.AccountDTO;
import com.jerin.accounts.dto.CustomerDto;
import com.jerin.accounts.entity.Accounts;
import com.jerin.accounts.entity.Customer;
import com.jerin.accounts.exception.CustomerAlreadyExistsException;
import com.jerin.accounts.exception.ResourceNotFoundException;
import com.jerin.accounts.mapper.AccountsMapper;
import com.jerin.accounts.mapper.CustomerMapper;
import com.jerin.accounts.repository.AccountRepository;
import com.jerin.accounts.repository.CustomerRepository;
import com.jerin.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository cusotmerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
       // cusotmerRepository.save(customerDto);
        Customer customer = CustomerMapper.maptoCustomer(new Customer(),customerDto);
        Optional<Customer>  optionalCustomer = cusotmerRepository.findBymobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw  new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedcustomer  = cusotmerRepository.save(customer);
        accountRepository.save(createNewAccount(savedcustomer));

    }

    private Accounts createNewAccount(Customer savedcustomer) {
        Accounts account = new Accounts();
        account.setCustomerId(savedcustomer.getCustomerId());
        long randomnumber = 1000000000L+ new Random().nextInt(900000000);
        account.setAccountNumber(randomnumber);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Anonymous");
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customeOutput = cusotmerRepository.findBymobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Accounts accountOutput = accountRepository.findBycustomerId(customeOutput.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","mobileNumber",mobileNumber)
        );
        CustomerDto CustomerDto = CustomerMapper.maptoCustomerDTO(customeOutput,new CustomerDto());
        CustomerDto.setAccountDTO(AccountsMapper.maptoAccoutDTO(accountOutput,new AccountDTO()));
        return CustomerDto ;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDTO accountsDTO = customerDto.getAccountDTO();
        if(accountsDTO!=null) {
            Accounts accountdetails = accountRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountsDTO.getAccountNumber().toString())
            );

            AccountsMapper.maptoAccounts(accountdetails, accountsDTO);
            accountdetails = accountRepository.save(accountdetails);
            Long customerID = accountdetails.getCustomerId();

            Customer customerdetails = cusotmerRepository.findById(customerID).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountsDTO.getAccountNumber().toString())
            );
            CustomerMapper.maptoCustomer(customerdetails,customerDto);
            cusotmerRepository.save(customerdetails);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = cusotmerRepository.findBymobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        Long customerID = customer.getCustomerId();
        cusotmerRepository.delete(customer);
        Accounts account = accountRepository.findBycustomerId(customerID).orElseThrow(
                () -> new ResourceNotFoundException("Customer","Customer ID",customerID.toString())
        );
        accountRepository.delete(account);
        return true;
    }

}
