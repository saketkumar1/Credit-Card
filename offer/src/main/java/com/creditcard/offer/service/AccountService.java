package com.creditcard.offer.service;

import com.creditcard.offer.dto.AccountDto;
import com.creditcard.offer.entity.Account;
import com.creditcard.offer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountDto account) {
         Account newAccount = new Account(account.getCustomer_Id(), account.getAccountLimit(), account.getPerTransactionLimit(),account.getLastAccountLimit(), account.getLastPerTransactionLimit());
         newAccount.setAccountLimitUpdateTime(LocalDate.now());
         newAccount.setPerTransactionLimitUpdateTime(LocalDate.now());
         return accountRepository.save(newAccount);

    }

    @Override
    public Optional<Account> findAccountById(long accountId) {
        return accountRepository.findById(accountId);
    }
}
