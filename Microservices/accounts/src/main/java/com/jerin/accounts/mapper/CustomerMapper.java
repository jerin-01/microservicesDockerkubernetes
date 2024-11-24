package com.jerin.accounts.mapper;

import com.jerin.accounts.dto.CustomerDto;
import com.jerin.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto maptoCustomerDTO(Customer customer , CustomerDto customerDto){
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
    public static Customer maptoCustomer(Customer customer , CustomerDto customerDto){
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());

        return customer;
    }
}
