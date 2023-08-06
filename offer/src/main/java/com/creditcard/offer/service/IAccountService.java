package com.creditcard.offer.service;

import com.creditcard.offer.dto.AccountDto;
import com.creditcard.offer.entity.Account;

import java.util.Optional;

public interface IAccountService {

    public Account createAccount(AccountDto account);
    public Optional<Account> findAccountById(long accountId);
}
