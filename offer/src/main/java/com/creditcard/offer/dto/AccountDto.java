package com.creditcard.offer.dto;

import lombok.Data;

@Data
public class AccountDto {

    public long customer_Id;
    public double accountLimit;
    public double perTransactionLimit;
    public double lastAccountLimit;
    public double lastPerTransactionLimit;
}
