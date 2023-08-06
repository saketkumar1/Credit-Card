package com.creditcard.offer.controller;

import com.creditcard.offer.dto.AccountDto;
import com.creditcard.offer.entity.Account;
import com.creditcard.offer.response.ApiResponse;
import com.creditcard.offer.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/create")
    public ApiResponse<Account> createAccount(@RequestBody AccountDto account){
        try {
            Account result =  accountService.createAccount(account);
            return new ApiResponse<>("Successfully added data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/{accountId}")
    public ApiResponse<Optional<Account>> findAccountById(@PathVariable long accountId){

        try {
            Optional<Account> result= accountService.findAccountById(accountId);
            ApiResponse<Optional<Account>> response = new ApiResponse<>("Successfully get data!", HttpStatus.OK, result);
            if(result == null){
                response.setMessage("No data found");
            }
            return response;
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
