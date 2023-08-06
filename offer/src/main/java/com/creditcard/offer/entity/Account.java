package com.creditcard.offer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="Account")
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long account_Id;
    private long customer_Id;
    private double accountLimit;
    private double perTransactionLimit;
    private double lastAccountLimit;
    private double lastPerTransactionLimit;


    private LocalDate accountLimitUpdateTime;
    private LocalDate perTransactionLimitUpdateTime;

    public Account(long customer_Id, double accountLimit, double perTransactionLimit, double lastAccountLimit, double lastPerTransactionLimit) {
        this.customer_Id = customer_Id;
        this.accountLimit = accountLimit;
        this.perTransactionLimit = perTransactionLimit;
        this.lastAccountLimit = lastAccountLimit;
        this.lastPerTransactionLimit = lastPerTransactionLimit;
    }
}
