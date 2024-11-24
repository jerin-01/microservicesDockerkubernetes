package com.jerin.accounts.mapper;

import com.jerin.accounts.dto.AccountDTO;
import com.jerin.accounts.dto.CustomerDto;
import com.jerin.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountDTO maptoAccoutDTO(Accounts account,AccountDTO accoutndto){
        accoutndto.setAccountNumber(account.getAccountNumber());
        accoutndto.setAccountType(account.getAccountType());
        accoutndto.setBranchAddress(account.getBranchAddress());
        return accoutndto;
    }

    public static Accounts maptoAccounts(Accounts account,AccountDTO accoutndto){
        account.setAccountNumber(accoutndto.getAccountNumber());
        account.setAccountType(accoutndto.getAccountType());
        account.setBranchAddress(account.getBranchAddress());
        return account;
    }
}
